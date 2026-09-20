package com.example.appandroid.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RayonDto(
    val id: String,
    val nm_rayon: String,
    val jumlah_jemaat: Int = 0,
)
