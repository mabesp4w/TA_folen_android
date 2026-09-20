package com.example.appandroid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jadwal")
data class JadwalEntity(
    @PrimaryKey val id: String,
    val nmIbadah: String,
    val unsur: String? = null,
    val tgl: String? = null,
    val waktu: String? = null,
    val tempat: String? = null,
    val rayon: String? = null,
    val pelayanFirman: String? = null,
    val catatan: String? = null,
    val cachedAt: Long = System.currentTimeMillis(),
)
