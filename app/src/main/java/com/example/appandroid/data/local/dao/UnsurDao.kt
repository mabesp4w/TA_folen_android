package com.example.appandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.appandroid.data.local.entity.UnsurEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UnsurDao {
    @Query("SELECT * FROM unsur ORDER BY nmUnsur ASC")
    fun getAll(): Flow<List<UnsurEntity>>

    @Query("SELECT COUNT(*) FROM unsur")
    fun count(): Flow<Int>

    @Upsert
    suspend fun upsertAll(items: List<UnsurEntity>)

    @Query("DELETE FROM unsur")
    suspend fun deleteAll()
}
