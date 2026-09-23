package com.example.appandroid.ui.screen

import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

// Pemformat tanggal berbahasa Indonesia yang dipakai bersama oleh layar data orang.
// Masukan berupa ISO "YYYY-MM-DD" dari API; keluaran "d MMM yyyy" (mis. "5 Jan 2020").
private val idLocaleTgl = Locale.of("id")
private val tanggalFormatter = DateTimeFormatter.ofPattern("d MMM yyyy", idLocaleTgl)
private val rupiahFormatter = NumberFormat.getInstance(idLocaleTgl)

fun formatTanggalId(tgl: String?): String {
    if (tgl.isNullOrBlank()) return ""
    return try {
        LocalDate.parse(tgl).format(tanggalFormatter)
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
