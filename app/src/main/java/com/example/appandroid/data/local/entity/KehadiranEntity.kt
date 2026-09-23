package com.example.appandroid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kehadiran")
data class KehadiranEntity(
    @PrimaryKey val id: String,
    val lakiLaki: Int = 0,
    val perempuan: Int = 0,
    val anak: Int = 0,
    val total: Int = 0,
    val ibadahNama: String? = null,
    val ibadahTgl: String? = null,
    val ibadahWaktu: String? = null,
    val cachedAt: Long = System.currentTimeMillis(),
)
