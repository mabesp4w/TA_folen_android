package com.example.appandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.appandroid.data.local.entity.JadwalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JadwalDao {
    @Query("SELECT * FROM jadwal ORDER BY tgl ASC, waktu ASC")
    fun getAll(): Flow<List<JadwalEntity>>

    @Query("SELECT * FROM jadwal ORDER BY tgl ASC, waktu ASC LIMIT :limit")
    fun getMendatang(limit: Int = 5): Flow<List<JadwalEntity>>

    @Upsert
    suspend fun upsertAll(items: List<JadwalEntity>)

    @Query("DELETE FROM jadwal")
    suspend fun deleteAll()
}
