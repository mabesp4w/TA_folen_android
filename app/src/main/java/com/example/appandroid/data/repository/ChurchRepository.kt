package com.example.appandroid.data.repository

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
import com.example.appandroid.data.remote.ChurchApiService
import com.example.appandroid.data.remote.dto.toEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChurchRepository @Inject constructor(
    private val api: ChurchApiService,
    private val renunganDao: RenunganDao,
    private val jadwalDao: JadwalDao,
    private val pengumumanDao: PengumumanDao,
    private val albumDao: AlbumDao,
    private val rayonDao: RayonDao,
    private val unsurDao: UnsurDao,
) {
    fun getRenunganTerbaru(): Flow<RenunganEntity?> = renunganDao.getTerbaru()
    fun getJadwalMendatang(): Flow<List<JadwalEntity>> = jadwalDao.getMendatang(5)
    fun getPengumumanTerbaru(): Flow<List<PengumumanEntity>> = pengumumanDao.getTerbaru(5)
    fun getAlbums(): Flow<List<AlbumEntity>> = albumDao.getAll()
    fun getRayonList(): Flow<List<RayonEntity>> = rayonDao.getAll()
    fun getRayonCount(): Flow<Int> = rayonDao.count()
    fun getUnsurCount(): Flow<Int> = unsurDao.count()
    fun getUnsurList(): Flow<List<UnsurEntity>> = unsurDao.getAll()

    suspend fun refreshRenunganTerbaru(): Result<Unit> = runCatching {
        val resp = api.getRenunganTerbaru()
        if (resp.success) renunganDao.upsert(resp.data.toEntity())
    }

    suspend fun refreshJadwal(): Result<Unit> = runCatching {
        val resp = api.getJadwalList(page = 1, limit = 10)
        if (resp.success) {
            jadwalDao.deleteAll()
            jadwalDao.upsertAll(resp.data.map { it.toEntity() })
        }
    }

    suspend fun refreshPengumuman(): Result<Unit> = runCatching {
        val resp = api.getPengumumanList(page = 1, limit = 10)
        if (resp.success) {
            pengumumanDao.deleteAll()
            pengumumanDao.upsertAll(resp.data.map { it.toEntity() })
        }
    }

    suspend fun refreshAlbums(): Result<Unit> = runCatching {
        val resp = api.getAlbumList(page = 1, limit = 20)
        if (resp.success) {
            albumDao.deleteAll()
            albumDao.upsertAll(resp.data.map { it.toEntity() })
        }
    }

    suspend fun refreshRayon(): Result<Unit> = runCatching {
        val resp = api.getRayonList()
        if (resp.success) {
            rayonDao.deleteAll()
            rayonDao.upsertAll(resp.data.map { it.toEntity() })
        }
    }

    suspend fun refreshUnsur(): Result<Unit> = runCatching {
        val resp = api.getUnsurList()
        if (resp.success) {
            unsurDao.deleteAll()
            unsurDao.upsertAll(resp.data.map { it.toEntity() })
        }
    }

    suspend fun refreshAll(): Result<Unit> = runCatching {
        refreshRenunganTerbaru()
        refreshJadwal()
        refreshPengumuman()
        refreshAlbums()
        refreshRayon()
        refreshUnsur()
    }
}
