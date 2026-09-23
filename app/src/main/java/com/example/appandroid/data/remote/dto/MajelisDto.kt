package com.example.appandroid.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MajelisDto(
    val id: String,
    val nama: String,
    val rayon: String? = null,
    val jabatan: String? = null,
    val tgl_mulai: String? = null,
    val tgl_selesai: String? = null,
    val aktif: Boolean = true,
)
