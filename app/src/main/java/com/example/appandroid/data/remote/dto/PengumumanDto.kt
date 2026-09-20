package com.example.appandroid.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PengumumanDto(
    val id: String,
    val judul: String,
    val isi: String? = null,
    val tgl_publish: String? = null,
)
