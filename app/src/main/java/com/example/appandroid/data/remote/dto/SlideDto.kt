package com.example.appandroid.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SlideDto(
    val id: String,
    val order: Int = 0,
    val url: String? = null,
    val judul: String? = null,
    val teks: String? = null,
    val link_url: String? = null,
    val link_teks: String? = null,
)
