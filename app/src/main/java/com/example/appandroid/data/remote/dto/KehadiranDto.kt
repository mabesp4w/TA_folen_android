package com.example.appandroid.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class KehadiranDto(
    val id: String,
    val laki_laki: Int = 0,
    val perempuan: Int = 0,
    val anak: Int = 0,
    val total: Int = 0,
    val ibadah: IbadahRefDto = IbadahRefDto(),
)
