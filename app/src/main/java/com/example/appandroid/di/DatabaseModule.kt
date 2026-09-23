package com.example.appandroid.di

import android.content.Context
import androidx.room.Room
import com.example.appandroid.data.local.ChurchDatabase
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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ChurchDatabase =
        Room.databaseBuilder(
            context,
            ChurchDatabase::class.java,
            "church_db",
        ).fallbackToDestructiveMigration(dropAllTables = true).build()

    @Provides fun provideRenunganDao(db: ChurchDatabase): RenunganDao = db.renunganDao()
    @Provides fun provideJadwalDao(db: ChurchDatabase): JadwalDao = db.jadwalDao()
    @Provides fun providePengumumanDao(db: ChurchDatabase): PengumumanDao = db.pengumumanDao()
    @Provides fun provideAlbumDao(db: ChurchDatabase): AlbumDao = db.albumDao()
    @Provides fun provideRayonDao(db: ChurchDatabase): RayonDao = db.rayonDao()
    @Provides fun provideUnsurDao(db: ChurchDatabase): UnsurDao = db.unsurDao()
    @Provides fun provideJemaatDao(db: ChurchDatabase): JemaatDao = db.jemaatDao()
    @Provides fun providePendetaDao(db: ChurchDatabase): PendetaDao = db.pendetaDao()
    @Provides fun provideMajelisDao(db: ChurchDatabase): MajelisDao = db.majelisDao()
    @Provides fun providePengurusDao(db: ChurchDatabase): PengurusDao = db.pengurusDao()
    @Provides fun provideKehadiranDao(db: ChurchDatabase): KehadiranDao = db.kehadiranDao()
    @Provides fun provideKeuanganDao(db: ChurchDatabase): KeuanganDao = db.keuanganDao()
}
