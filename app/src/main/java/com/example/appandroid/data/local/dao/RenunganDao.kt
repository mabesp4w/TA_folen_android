package com.example.appandroid.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.appandroid.data.local.entity.RenunganEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RenunganDao {
    @Query("SELECT * FROM renungan ORDER BY tglPublish DESC LIMIT 1")
    fun getTerbaru(): Flow<RenunganEntity?>

    @Query("SELECT * FROM renungan ORDER BY tglPublish DESC")
    fun getAll(): Flow<List<RenunganEntity>>

    @Upsert
    suspend fun upsertAll(items: List<RenunganEntity>)

    @Upsert
    suspend fun upsert(item: RenunganEntity)

    @Query("DELETE FROM renungan")
    suspend fun deleteAll()
}
