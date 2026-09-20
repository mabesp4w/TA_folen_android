package com.example.appandroid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pengumuman")
data class PengumumanEntity(
    @PrimaryKey val id: String,
    val judul: String,
    val isi: String? = null,
    val tglPublish: String? = null,
    val cachedAt: Long = System.currentTimeMillis(),
)
