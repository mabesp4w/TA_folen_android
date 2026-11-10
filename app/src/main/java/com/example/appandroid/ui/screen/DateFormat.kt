package com.example.appandroid.ui.screen

import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

// Pemformat tanggal yang dipakai bersama oleh layar-layar.
// Masukan berupa ISO "YYYY-MM-DD" dari API; keluaran "dd-MM-yyyy" (mis. "05-01-2020").
private val idLocaleTgl = Locale.of("id")
private val tanggalFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", idLocaleTgl)
private val rupiahFormatter = NumberFormat.getInstance(idLocaleTgl)

fun formatTanggalId(tgl: String?): String {
    if (tgl.isNullOrBlank()) return ""
    return try {
        LocalDate.parse(tgl).format(tanggalFormatter)
    } catch (_: Exception) {
        tgl
    }
}

/**
 * Format tanggal publikasi (warta/renungan) menjadi "dd-MM-yyyy".
 * API bisa mengirim "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", atau ISO dengan "T";
 * bagian jam dibuang. Jika tidak bisa diparse, teks asli dikembalikan apa adanya.
 */
fun formatTanggalPublish(tgl: String?): String {
    if (tgl.isNullOrBlank()) return ""
    return try {
        val tanggalSaja = tgl.substringBefore(' ').substringBefore('T')
        LocalDate.parse(tanggalSaja).format(tanggalFormatter)
    } catch (_: Exception) {
        tgl
    }
}

// Nominal rupiah dengan pemisah ribuan gaya Indonesia (mis. 1500000 -> "Rp 1.500.000").
fun formatRupiah(jumlah: Long): String = "Rp " + rupiahFormatter.format(jumlah)

// Kategori dari API berformat UPPER_SNAKE_CASE -> "Title Case" (mis. "PENGUCAPAN_SYUKUR" -> "Pengucapan Syukur").
fun formatKategori(kategori: String?): String {
    if (kategori.isNullOrBlank()) return ""
    return kategori.split("_").joinToString(" ") { word ->
        word.lowercase(idLocaleTgl).replaceFirstChar { it.titlecase(idLocaleTgl) }
    }
}
