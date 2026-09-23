package com.example.appandroid.data.remote.dto

import kotlinx.serialization.Serializable

// Ringkasan ibadah yang menyertai data kehadiran & keuangan.
@Serializable
data class IbadahRefDto(
    val nm_ibadah: String? = null,
    val tgl: String? = null,
    val waktu: String? = null,
)
