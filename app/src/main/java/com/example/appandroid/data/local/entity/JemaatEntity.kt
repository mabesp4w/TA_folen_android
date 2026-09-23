package com.example.appandroid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jemaat")
data class JemaatEntity(
    @PrimaryKey val id: String,
    val nmLengkap: String,
    val tglLahir: String? = null,
    val jenkel: String? = null,
    val statKeluarga: String? = null,
    val noKk: String? = null,
    val alamatBlok: String? = null,
    val status: String? = null,
    val rayon: String? = null,
    val cachedAt: Long = System.currentTimeMillis(),
)
