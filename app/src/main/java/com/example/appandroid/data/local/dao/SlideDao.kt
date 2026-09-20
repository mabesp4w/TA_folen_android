package com.example.appandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.appandroid.data.local.entity.SlideEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SlideDao {
    @Query("SELECT * FROM slide ORDER BY `order` ASC")
    fun getAll(): Flow<List<SlideEntity>>

    @Upsert
    suspend fun upsertAll(items: List<SlideEntity>)

    @Query("DELETE FROM slide")
    suspend fun deleteAll()
}
