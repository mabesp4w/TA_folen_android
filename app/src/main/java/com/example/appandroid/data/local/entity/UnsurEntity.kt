package com.example.appandroid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "unsur")
data class UnsurEntity(
    @PrimaryKey val id: String,
    val nmUnsur: String,
    val cachedAt: Long = System.currentTimeMillis(),
)
