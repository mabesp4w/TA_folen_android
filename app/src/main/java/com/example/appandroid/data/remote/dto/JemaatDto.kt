package com.example.appandroid.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class JemaatDto(
    val id: String,
    val nm_lengkap: String,
    val tgl_lahir: String? = null,
    val jenkel: String? = null,
    val stat_keluarga: String? = null,
    val id_keluarga: String? = null,
    val no_kk: String? = null,
    val alamat_blok: String? = null,
    val status: String? = null,
    val rayon: String? = null,
    val id_rayon: String? = null,
)
