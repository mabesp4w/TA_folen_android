package com.example.appandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.appandroid.data.local.entity.PengumumanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PengumumanDao {
    @Query("SELECT * FROM pengumuman ORDER BY tglPublish DESC")
    fun getAll(): Flow<List<PengumumanEntity>>

    @Query("SELECT * FROM pengumuman ORDER BY tglPublish DESC LIMIT :limit")
    fun getTerbaru(limit: Int = 5): Flow<List<PengumumanEntity>>

    @Upsert
    suspend fun upsertAll(items: List<PengumumanEntity>)

    @Query("DELETE FROM pengumuman")
    suspend fun deleteAll()
}
