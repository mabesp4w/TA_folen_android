package com.example.appandroid.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appandroid.data.local.dao.AlbumDao
import com.example.appandroid.data.local.dao.JadwalDao
import com.example.appandroid.data.local.dao.JemaatDao
import com.example.appandroid.data.local.dao.KehadiranDao
import com.example.appandroid.data.local.dao.KeuanganDao
import com.example.appandroid.data.local.dao.MajelisDao
import com.example.appandroid.data.local.dao.PendetaDao
import com.example.appandroid.data.local.dao.PengumumanDao
import com.example.appandroid.data.local.dao.PengurusDao
import com.example.appandroid.data.local.dao.RayonDao
import com.example.appandroid.data.local.dao.RenunganDao
import com.example.appandroid.data.local.dao.UnsurDao
import com.example.appandroid.data.local.entity.AlbumEntity
import com.example.appandroid.data.local.entity.JadwalEntity
import com.example.appandroid.data.local.entity.JemaatEntity
import com.example.appandroid.data.local.entity.KehadiranEntity
import com.example.appandroid.data.local.entity.KeuanganEntity
import com.example.appandroid.data.local.entity.MajelisEntity
import com.example.appandroid.data.local.entity.PendetaEntity
import com.example.appandroid.data.local.entity.PengumumanEntity
import com.example.appandroid.data.local.entity.PengurusEntity
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
        JemaatEntity::class,
        PendetaEntity::class,
        MajelisEntity::class,
        PengurusEntity::class,
        KehadiranEntity::class,
        KeuanganEntity::class,
    ],
    version = 4,
    exportSchema = false,
)
abstract class ChurchDatabase : RoomDatabase() {
    abstract fun renunganDao(): RenunganDao
    abstract fun jadwalDao(): JadwalDao
    abstract fun pengumumanDao(): PengumumanDao
    abstract fun albumDao(): AlbumDao
    abstract fun rayonDao(): RayonDao
    abstract fun unsurDao(): UnsurDao
    abstract fun jemaatDao(): JemaatDao
    abstract fun pendetaDao(): PendetaDao
    abstract fun majelisDao(): MajelisDao
    abstract fun pengurusDao(): PengurusDao
    abstract fun kehadiranDao(): KehadiranDao
    abstract fun keuanganDao(): KeuanganDao
}
