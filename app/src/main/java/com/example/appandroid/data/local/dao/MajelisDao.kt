package com.example.appandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.appandroid.data.local.entity.MajelisEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MajelisDao {
    @Query("SELECT * FROM majelis ORDER BY aktif DESC, jabatan ASC, nama ASC")
    fun getAll(): Flow<List<MajelisEntity>>

    @Upsert
    suspend fun upsertAll(items: List<MajelisEntity>)

    @Query("DELETE FROM majelis")
    suspend fun deleteAll()
}
