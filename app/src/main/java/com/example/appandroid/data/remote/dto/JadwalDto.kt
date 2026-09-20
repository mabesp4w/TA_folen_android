package com.example.appandroid.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class JadwalDto(
    val id: String,
    val nm_ibadah: String,
    val unsur: String? = null,
    val gabungan: Boolean? = null,
    val sektor: Int? = null,
    val tgl: String? = null,
    val waktu: String? = null,
    val tempat: String? = null,
    val alamat_tempat: String? = null,
    val rayon: String? = null,
    val tuan_rumah: String? = null,
    val pelayan_firman: String? = null,
    val liturgos: String? = null,
    val pembacaan_alkitab: String? = null,
    val piket: String? = null,
    val p_organ: String? = null,
    val catatan: String? = null,
)
