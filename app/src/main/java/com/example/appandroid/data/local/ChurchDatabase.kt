package com.example.appandroid.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appandroid.data.local.dao.AlbumDao
import com.example.appandroid.data.local.dao.JadwalDao
import com.example.appandroid.data.local.dao.PengumumanDao
import com.example.appandroid.data.local.dao.RayonDao
import com.example.appandroid.data.local.dao.RenunganDao
import com.example.appandroid.data.local.dao.UnsurDao
import com.example.appandroid.data.local.entity.AlbumEntity
import com.example.appandroid.data.local.entity.JadwalEntity
import com.example.appandroid.data.local.entity.PengumumanEntity
import com.example.appandroid.data.local.entity.RayonEntity
import com.example.appandroid.data.local.entity.RenunganEntity
import com.example.appandroid.data.local.entity.UnsurEntity

@Database(
    entities = [
        RenunganEntity::class,
        JadwalEntity::class,
        PengumumanEntity::class,
        AlbumEntity::class,
        RayonEntity::class,
        UnsurEntity::class,
    ],
    version = 2,
    exportSchema = false,
)
abstract class ChurchDatabase : RoomDatabase() {
    abstract fun renunganDao(): RenunganDao
    abstract fun jadwalDao(): JadwalDao
    abstract fun pengumumanDao(): PengumumanDao
    abstract fun albumDao(): AlbumDao
    abstract fun rayonDao(): RayonDao
    abstract fun unsurDao(): UnsurDao
}
