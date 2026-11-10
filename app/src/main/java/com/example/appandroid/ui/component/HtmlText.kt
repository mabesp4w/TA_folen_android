package com.example.appandroid.ui.component

import android.text.style.BulletSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.isSpecified
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat

/**
 * Menampilkan string yang berisi kode HTML (misal `<p>`, `<strong>`, `<em>`,
 * `<ul>/<li>`, `<br>`, `<a>`) dengan format yang sesuai fungsinya.
 *
 * Tag yang didukung:
 * - `<b>/<strong>`, `<i>/<em>`, `<u>`, `<s>/<strike>`
 * - `<h1>`–`<h6>` dan teks berukuran relatif lainnya
 * - `<ul>/<ol>/<li>` (bullet)
 * - `<br>`, `<p>` (baris baru / pemisah paragraf)
 * - `<a href>` (tautan yang bisa diklik)
 * - `<blockquote>` (indentasi), `<font color>` tidak diubah agar mengikuti tema
 */
@Composable
fun HtmlText(
    html: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: Color = Color.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
) {
    val linkColor = MaterialTheme.colorScheme.primary
    val annotated = remember(html, style.fontSize, linkColor) {
        htmlToAnnotatedString(html, style.fontSize, linkColor)
    }
    Text(
        text = annotated,
        modifier = modifier,
        color = color,
        style = style,
        maxLines = maxLines,
        overflow = overflow,
    )
}

/** Konversi HTML menjadi [AnnotatedString] dengan span yang sesuai. */
internal fun htmlToAnnotatedString(
    html: String,
    baseFontSize: TextUnit,
    linkColor: Color,
): AnnotatedString {
    if (html.isBlank()) return AnnotatedString("")

    // Bersihkan tag yang tidak bisa dirender sebagai teks (gambar, script, style).
    val cleaned = html
        .replace(Regex("<img[^>]*>", RegexOption.IGNORE_CASE), "")
        .replace(Regex("<script[\\s\\S]*?</script>", RegexOption.IGNORE_CASE), "")
        .replace(Regex("<style[\\s\\S]*?</style>", RegexOption.IGNORE_CASE), "")

    // Tanpa tag maupun entitas HTML, tidak perlu di-parse.
    if (!cleaned.contains('<') && !cleaned.contains('&')) return AnnotatedString(cleaned.trim())

    val spanned = HtmlCompat.fromHtml(cleaned, HtmlCompat.FROM_HTML_MODE_COMPACT)
    val text = spanned.toString()

    // Buang whitespace di awal/akhir hasil parse (newline sisa tag penutup, dsb).
    val start = text.indexOfFirst { !it.isWhitespace() }
    if (start == -1) return AnnotatedString("")
    val end = text.indexOfLast { !it.isWhitespace() } + 1 // exclusive

    // Bullet list: BulletSpan tidak didukung AnnotatedString,
    // jadi sisipkan karakter bullet di awal setiap baris item.
    val insertions = sortedMapOf<Int, MutableSet<String>>()
    for (bullet in spanned.getSpans(start, end, BulletSpan::class.java)) {
        val bs = maxOf(spanned.getSpanStart(bullet), start)
        val be = minOf(spanned.getSpanEnd(bullet), end)
        if (be <= bs) continue
        insertions.getOrPut(bs) { mutableSetOf() }.add("•  ")
        for (i in bs until be) {
            if (text[i] == '\n') insertions.getOrPut(i + 1) { mutableSetOf() }.add("•  ")
        }
    }

    // Bangun teks final + peta indeks lama -> baru.
    val sb = StringBuilder()
    val map = IntArray(text.length + 1) { -1 }
    for (i in start..end) {
        insertions[i]?.forEach { sb.append(it) }
        map[i] = sb.length
        if (i < end) sb.append(text[i])
    }

    return buildAnnotatedString {
        append(sb)

        data class SpanInfo(val start: Int, val end: Int, val style: SpanStyle, val url: String? = null)

        val spans = mutableListOf<SpanInfo>()
        for (span in spanned.getSpans(start, end, Any::class.java)) {
            val s = map[maxOf(spanned.getSpanStart(span), start)]
            val e = map[minOf(spanned.getSpanEnd(span), end)]
            if (s == -1 || e == -1 || e <= s) continue
            val style: SpanStyle = when (span) {
                is StyleSpan -> SpanStyle(
                    fontWeight = if (span.style and android.graphics.Typeface.BOLD != 0) FontWeight.Bold else null,
                    fontStyle = if (span.style and android.graphics.Typeface.ITALIC != 0) FontStyle.Italic else null,
                )
                is UnderlineSpan -> SpanStyle(textDecoration = TextDecoration.Underline)
                is StrikethroughSpan -> SpanStyle(textDecoration = TextDecoration.LineThrough)
                is RelativeSizeSpan -> if (baseFontSize.isSpecified && span.sizeChange > 0f) {
                    SpanStyle(fontSize = (baseFontSize.value * span.sizeChange).sp)
                } else {
                    SpanStyle()
                }
                is TypefaceSpan -> if (span.family == "monospace") {
                    SpanStyle(fontFamily = FontFamily.Monospace)
                } else {
                    SpanStyle()
                }
                is URLSpan -> SpanStyle(
                    color = linkColor,
                    textDecoration = TextDecoration.Underline,
                )
                else -> SpanStyle()
            }
            if (style == SpanStyle()) continue
            spans.add(SpanInfo(s, e, style, (span as? URLSpan)?.url))
        }

        // Urutkan agar span bersarang di-push dalam urutan yang benar.
        spans.sortWith(compareBy({ it.start }, { -it.end }))
        for (item in spans) {
            val index = if (item.url != null) {
                pushLink(LinkAnnotation.Url(item.url, TextLinkStyles(item.style)))
            } else {
                pushStyle(item.style)
            }
            pop(index)
        }
    }
}
