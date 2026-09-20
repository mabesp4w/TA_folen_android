package com.example.appandroid.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RenunganDto(
    val id: String,
    val judul: String,
    val bacaan: String? = null,
    val kutipan: String? = null,
    val isi: String? = null,
    val sumber: String? = null,
    val tgl_publish: String? = null,
)
