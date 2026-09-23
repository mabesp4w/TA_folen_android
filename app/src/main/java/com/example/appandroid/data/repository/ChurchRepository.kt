package com.example.appandroid.data.repository

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
import com.example.appandroid.data.remote.ChurchApiService
import com.example.appandroid.data.remote.dto.AlbumDetailDto
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
    private val jemaatDao: JemaatDao,
    private val pendetaDao: PendetaDao,
    private val majelisDao: MajelisDao,
    private val pengurusDao: PengurusDao,
    private val kehadiranDao: KehadiranDao,
    private val keuanganDao: KeuanganDao,
) {
    fun getRenunganTerbaru(): Flow<RenunganEntity?> = renunganDao.getTerbaru()
    fun getRenunganList(): Flow<List<RenunganEntity>> = renunganDao.getAll()
    fun getJadwalMendatang(): Flow<List<JadwalEntity>> = jadwalDao.getMendatang(5)
    fun getJadwalList(): Flow<List<JadwalEntity>> = jadwalDao.getAll()
    fun getPengumumanTerbaru(): Flow<List<PengumumanEntity>> = pengumumanDao.getTerbaru(5)
    fun getPengumumanList(): Flow<List<PengumumanEntity>> = pengumumanDao.getAll()
    fun getAlbums(): Flow<List<AlbumEntity>> = albumDao.getAll()
    fun getRayonList(): Flow<List<RayonEntity>> = rayonDao.getAll()
    fun getRayonCount(): Flow<Int> = rayonDao.count()
    fun getUnsurCount(): Flow<Int> = unsurDao.count()
    fun getUnsurList(): Flow<List<UnsurEntity>> = unsurDao.getAll()
    fun getJemaatList(): Flow<List<JemaatEntity>> = jemaatDao.getAll()
    fun getJemaatCount(): Flow<Int> = jemaatDao.count()
    fun getPendetaList(): Flow<List<PendetaEntity>> = pendetaDao.getAll()
    fun getMajelisList(): Flow<List<MajelisEntity>> = majelisDao.getAll()
    fun getPengurusList(): Flow<List<PengurusEntity>> = pengurusDao.getAll()
    fun getKehadiranList(): Flow<List<KehadiranEntity>> = kehadiranDao.getAll()
    fun getKeuanganList(): Flow<List<KeuanganEntity>> = keuanganDao.getAll()

    suspend fun refreshRenunganTerbaru(): Result<Unit> = runCatching {
        val resp = api.getRenunganTerbaru()
        if (resp.success) renunganDao.upsert(resp.data.toEntity())
    }

    suspend fun refreshRenunganList(): Result<Unit> = runCatching {
        val resp = api.getRenunganList(page = 1, limit = 20)
        if (resp.success) {
            renunganDao.deleteAll()
            renunganDao.upsertAll(resp.data.map { it.toEntity() })
        }
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

    // Detail album (daftar foto) diambil langsung dari API — tidak di-cache karena
    // tidak ada entitas Room untuk foto.
    suspend fun getAlbumDetail(id: String): Result<AlbumDetailDto> = runCatching {
        val resp = api.getAlbumDetail(id)
        if (!resp.success) error("Album tidak ditemukan")
        resp.data
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

    suspend fun refreshJemaat(): Result<Unit> = runCatching {
        val resp = api.getJemaatList(page = 1, limit = 100)
        if (resp.success) {
            jemaatDao.deleteAll()
            jemaatDao.upsertAll(resp.data.map { it.toEntity() })
        }
    }

    suspend fun refreshPendeta(): Result<Unit> = runCatching {
        val resp = api.getPendetaList()
        if (resp.success) {
            pendetaDao.deleteAll()
            pendetaDao.upsertAll(resp.data.map { it.toEntity() })
        }
    }

    suspend fun refreshMajelis(): Result<Unit> = runCatching {
        val resp = api.getMajelisList()
        if (resp.success) {
            majelisDao.deleteAll()
            majelisDao.upsertAll(resp.data.map { it.toEntity() })
        }
    }

    suspend fun refreshPengurus(): Result<Unit> = runCatching {
        val resp = api.getBadanPengurusList()
        if (resp.success) {
            pengurusDao.deleteAll()
            pengurusDao.upsertAll(resp.data.map { it.toEntity() })
        }
    }

    suspend fun refreshKehadiran(): Result<Unit> = runCatching {
        val resp = api.getKehadiranList(page = 1, limit = 100)
        if (resp.success) {
            kehadiranDao.deleteAll()
            kehadiranDao.upsertAll(resp.data.map { it.toEntity() })
        }
    }

    suspend fun refreshKeuangan(): Result<Unit> = runCatching {
        val resp = api.getKeuanganList(page = 1, limit = 200)
        if (resp.success) {
            keuanganDao.deleteAll()
            keuanganDao.upsertAll(resp.data.map { it.toEntity() })
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
