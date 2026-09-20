package com.example.appandroid.ui.sample

import androidx.compose.ui.graphics.vector.ImageVector

// Data contoh untuk pratinjau tampilan. Lapisan data (Room + API) menyusul
// pada tahap berikutnya sesuai spesifikasi offline-first.

data class MenuItem(
    val id: String,
    val label: String,
    val icon: ImageVector,
)

data class JadwalItem(
    val namaIbadah: String,
    val hari: String,
    val tanggal: String,
    val waktu: String,
    val tempat: String,
    val rayon: String?,
)

data class WartaItem(
    val judul: String,
    val cuplikan: String,
    val tanggal: String,
)

data class RenunganItem(
    val judul: String,
    val bacaan: String,
    val kutipan: String,
    val tanggal: String,
)

data class AlbumItem(
    val judul: String,
    val jumlahFoto: Int,
    val kategori: String,
)

object SampleData {
    val renunganHariIni = RenunganItem(
        judul = "Berakar dan Bertumbuh dalam Kasih",
        bacaan = "Efesus 3:14-21",
        kutipan = "Kiranya kamu berakar serta berdasar di dalam kasih.",
        tanggal = "Sabtu, 20 September 2026",
    )

    val jadwalMendatang = listOf(
        JadwalItem("Ibadah Minggu Raya", "Minggu", "21 Sep", "09.00", "Gereja Kanaan", null),
        JadwalItem("Ibadah Keluarga Rayon Galilea", "Selasa", "23 Sep", "18.30", "Kel. Pekpekay", "Galilea"),
        JadwalItem("Ibadah PW Sektor 3", "Kamis", "25 Sep", "17.00", "Kel. Wamafma", "Hermon"),
    )

    val warta = listOf(
        WartaItem(
            "Sakramen Baptisan Kudus",
            "Pelayanan baptisan kudus akan dilaksanakan pada ibadah Minggu mendatang bagi keluarga yang telah mendaftar.",
            "18 Sep 2026",
        ),
        WartaItem(
            "Latihan Paduan Suara Gabungan",
            "Seluruh anggota paduan suara diharapkan hadir untuk persiapan ibadah HUT jemaat.",
            "17 Sep 2026",
        ),
        WartaItem(
            "Persembahan Pembangunan",
            "Panitia mengucap syukur atas partisipasi jemaat dalam pembangunan gedung serba guna.",
            "15 Sep 2026",
        ),
    )

    val albums = listOf(
        AlbumItem("Ibadah Natal 2025", 24, "Natal"),
        AlbumItem("Baptisan Kudus", 12, "Baptisan"),
        AlbumItem("HUT Jemaat ke-30", 41, "Perayaan"),
        AlbumItem("Paskah 2026", 18, "Paskah"),
    )
}
