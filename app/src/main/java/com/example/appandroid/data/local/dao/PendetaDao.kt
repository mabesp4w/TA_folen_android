package com.example.appandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.appandroid.data.local.entity.PendetaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PendetaDao {
    @Query("SELECT * FROM pendeta ORDER BY aktif DESC, nama ASC")
    fun getAll(): Flow<List<PendetaEntity>>

    @Upsert
    suspend fun upsertAll(items: List<PendetaEntity>)

    @Query("DELETE FROM pendeta")
    suspend fun deleteAll()
}
