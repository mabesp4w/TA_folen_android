package com.example.appandroid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rayon")
data class RayonEntity(
    @PrimaryKey val id: String,
    val nmRayon: String,
    val jumlahJemaat: Int = 0,
    val cachedAt: Long = System.currentTimeMillis(),
)
