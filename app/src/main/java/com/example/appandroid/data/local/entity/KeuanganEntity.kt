package com.example.appandroid.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keuangan")
data class KeuanganEntity(
    @PrimaryKey val id: String,
    val kategori: String,
    val nmPemberi: String? = null,
    val jumlah: Long = 0,
    val keterangan: String? = null,
    val createdAt: String? = null,
    val ibadahNama: String? = null,
    val ibadahTgl: String? = null,
    val ibadahWaktu: String? = null,
    val cachedAt: Long = System.currentTimeMillis(),
)
