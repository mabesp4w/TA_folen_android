package com.example.appandroid.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class KeuanganDto(
    val id: String,
    val kategori: String,
    val nm_pemberi: String? = null,
    val jumlah: Long = 0,
    val keterangan: String? = null,
    val created_at: String? = null,
    val ibadah: IbadahRefDto? = null,
)
