package com.example.appandroid.di

import android.content.Context
import androidx.room.Room
import com.example.appandroid.data.local.ChurchDatabase
import com.example.appandroid.data.local.dao.AlbumDao
import com.example.appandroid.data.local.dao.JadwalDao
import com.example.appandroid.data.local.dao.PengumumanDao
import com.example.appandroid.data.local.dao.RayonDao
import com.example.appandroid.data.local.dao.RenunganDao
import com.example.appandroid.data.local.dao.SlideDao
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
    @Provides fun provideSlideDao(db: ChurchDatabase): SlideDao = db.slideDao()
    @Provides fun provideRayonDao(db: ChurchDatabase): RayonDao = db.rayonDao()
    @Provides fun provideUnsurDao(db: ChurchDatabase): UnsurDao = db.unsurDao()
}
