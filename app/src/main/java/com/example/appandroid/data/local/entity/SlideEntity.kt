package com.example.appandroid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "slide")
data class SlideEntity(
    @PrimaryKey val id: String,
    val order: Int = 0,
    val url: String? = null,
    val judul: String? = null,
    val teks: String? = null,
    val linkUrl: String? = null,
    val linkTeks: String? = null,
    val cachedAt: Long = System.currentTimeMillis(),
)
