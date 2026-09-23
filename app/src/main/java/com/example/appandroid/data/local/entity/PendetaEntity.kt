package com.example.appandroid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pendeta")
data class PendetaEntity(
    @PrimaryKey val id: String,
    val nama: String,
    val rayon: String? = null,
    val gelar: String? = null,
    val tglMulai: String? = null,
    val tglSelesai: String? = null,
    val aktif: Boolean = true,
    val cachedAt: Long = System.currentTimeMillis(),
)
