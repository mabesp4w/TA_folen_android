package com.example.appandroid.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AlbumDto(
    val id: String,
    val judul: String,
    val keterangan: String? = null,
    val kategori: String? = null,
    val tgl: String? = null,
    val jumlah_foto: Int = 0,
    val sampul: String? = null,
)

@Serializable
data class AlbumDetailDto(
    val id: String,
    val judul: String,
    val keterangan: String? = null,
    val kategori: String? = null,
    val tgl: String? = null,
    val foto: List<FotoDto> = emptyList(),
)

@Serializable
data class FotoDto(
    val id: String,
    val url: String? = null,
    val caption: String? = null,
)
