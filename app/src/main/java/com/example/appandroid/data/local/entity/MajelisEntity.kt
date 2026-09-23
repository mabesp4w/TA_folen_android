package com.example.appandroid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "majelis")
data class MajelisEntity(
    @PrimaryKey val id: String,
    val nama: String,
    val rayon: String? = null,
    val jabatan: String? = null,
    val tglMulai: String? = null,
    val tglSelesai: String? = null,
    val aktif: Boolean = true,
    val cachedAt: Long = System.currentTimeMillis(),
)
