package com.example.appandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.appandroid.data.local.entity.KeuanganEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface KeuanganDao {
    @Query("SELECT * FROM keuangan ORDER BY createdAt DESC")
    fun getAll(): Flow<List<KeuanganEntity>>

    @Upsert
    suspend fun upsertAll(items: List<KeuanganEntity>)

    @Query("DELETE FROM keuangan")
    suspend fun deleteAll()
}
