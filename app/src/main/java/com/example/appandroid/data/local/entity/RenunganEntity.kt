package com.example.appandroid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "renungan")
data class RenunganEntity(
    @PrimaryKey val id: String,
    val judul: String,
    val bacaan: String? = null,
    val kutipan: String? = null,
    val isi: String? = null,
    val sumber: String? = null,
    val tglPublish: String? = null,
    val cachedAt: Long = System.currentTimeMillis(),
)
