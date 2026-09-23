package com.example.appandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.appandroid.data.local.entity.JemaatEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JemaatDao {
    @Query("SELECT * FROM jemaat ORDER BY nmLengkap ASC")
    fun getAll(): Flow<List<JemaatEntity>>

    @Query("SELECT COUNT(*) FROM jemaat")
    fun count(): Flow<Int>

    @Upsert
    suspend fun upsertAll(items: List<JemaatEntity>)

    @Query("DELETE FROM jemaat")
    suspend fun deleteAll()
}
