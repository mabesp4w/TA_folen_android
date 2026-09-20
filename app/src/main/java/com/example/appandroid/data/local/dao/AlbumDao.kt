package com.example.appandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.appandroid.data.local.entity.AlbumEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {
    @Query("SELECT * FROM album ORDER BY tgl DESC")
    fun getAll(): Flow<List<AlbumEntity>>

    @Upsert
    suspend fun upsertAll(items: List<AlbumEntity>)

    @Query("DELETE FROM album")
    suspend fun deleteAll()
}
