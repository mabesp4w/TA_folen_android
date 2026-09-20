package com.example.appandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.appandroid.data.local.entity.RayonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RayonDao {
    @Query("SELECT * FROM rayon ORDER BY nmRayon ASC")
    fun getAll(): Flow<List<RayonEntity>>

    @Query("SELECT COUNT(*) FROM rayon")
    fun count(): Flow<Int>

    @Upsert
    suspend fun upsertAll(items: List<RayonEntity>)

    @Query("DELETE FROM rayon")
    suspend fun deleteAll()
}
