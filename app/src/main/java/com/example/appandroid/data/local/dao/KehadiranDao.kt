package com.example.appandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.appandroid.data.local.entity.KehadiranEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface KehadiranDao {
    @Query("SELECT * FROM kehadiran ORDER BY ibadahTgl DESC")
    fun getAll(): Flow<List<KehadiranEntity>>

    @Upsert
    suspend fun upsertAll(items: List<KehadiranEntity>)

    @Query("DELETE FROM kehadiran")
    suspend fun deleteAll()
}
