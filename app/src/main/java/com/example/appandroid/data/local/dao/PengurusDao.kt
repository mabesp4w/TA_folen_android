package com.example.appandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.appandroid.data.local.entity.PengurusEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PengurusDao {
    @Query("SELECT * FROM badan_pengurus ORDER BY aktif DESC, nama ASC")
    fun getAll(): Flow<List<PengurusEntity>>

    @Upsert
    suspend fun upsertAll(items: List<PengurusEntity>)

    @Query("DELETE FROM badan_pengurus")
    suspend fun deleteAll()
}
