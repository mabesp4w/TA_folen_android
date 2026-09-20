package com.example.appandroid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "album")
data class AlbumEntity(
    @PrimaryKey val id: String,
    val judul: String,
    val keterangan: String? = null,
    val kategori: String? = null,
    val tgl: String? = null,
    val jumlahFoto: Int = 0,
    val sampul: String? = null,
    val cachedAt: Long = System.currentTimeMillis(),
)
