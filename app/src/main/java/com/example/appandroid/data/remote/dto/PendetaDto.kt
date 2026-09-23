package com.example.appandroid.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PendetaDto(
    val id: String,
    val nama: String,
    val rayon: String? = null,
    val gelar: String? = null,
    val tgl_mulai: String? = null,
    val tgl_selesai: String? = null,
    val aktif: Boolean = true,
)
