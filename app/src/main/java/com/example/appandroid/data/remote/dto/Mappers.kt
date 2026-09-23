package com.example.appandroid.data.remote.dto

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

fun RenunganDto.toEntity() = RenunganEntity(
    id = id,
    judul = judul,
    bacaan = bacaan,
    kutipan = kutipan,
    isi = isi,
    sumber = sumber,
    tglPublish = tgl_publish,
)

fun JadwalDto.toEntity() = JadwalEntity(
    id = id,
    nmIbadah = nm_ibadah,
    unsur = unsur,
    tgl = tgl,
    waktu = waktu,
    tempat = tempat,
    rayon = rayon,
    pelayanFirman = pelayan_firman,
    catatan = catatan,
)

fun PengumumanDto.toEntity() = PengumumanEntity(
    id = id,
    judul = judul,
    isi = isi,
    tglPublish = tgl_publish,
)

fun AlbumDto.toEntity() = AlbumEntity(
    id = id,
    judul = judul,
    keterangan = keterangan,
    kategori = kategori,
    tgl = tgl,
    jumlahFoto = jumlah_foto,
    sampul = sampul,
)

fun RayonDto.toEntity() = RayonEntity(
    id = id,
    nmRayon = nm_rayon,
    jumlahJemaat = jumlah_jemaat,
)

fun UnsurDto.toEntity() = UnsurEntity(
    id = id,
    nmUnsur = nm_unsur,
)

fun JemaatDto.toEntity() = JemaatEntity(
    id = id,
    nmLengkap = nm_lengkap,
    tglLahir = tgl_lahir,
    jenkel = jenkel,
    statKeluarga = stat_keluarga,
    noKk = no_kk,
    alamatBlok = alamat_blok,
    status = status,
    rayon = rayon,
)

fun PendetaDto.toEntity() = PendetaEntity(
    id = id,
    nama = nama,
    rayon = rayon,
    gelar = gelar,
    tglMulai = tgl_mulai,
    tglSelesai = tgl_selesai,
    aktif = aktif,
)

fun MajelisDto.toEntity() = MajelisEntity(
    id = id,
    nama = nama,
    rayon = rayon,
    jabatan = jabatan,
    tglMulai = tgl_mulai,
    tglSelesai = tgl_selesai,
    aktif = aktif,
)

fun BadanPengurusDto.toEntity() = PengurusEntity(
    id = id,
    nama = nama,
    rayon = rayon,
    unsur = unsur,
    sektor = sektor,
    jabatan = jabatan,
    tglMulai = tgl_mulai,
    tglSelesai = tgl_selesai,
    aktif = aktif,
)

fun KehadiranDto.toEntity() = KehadiranEntity(
    id = id,
    lakiLaki = laki_laki,
    perempuan = perempuan,
    anak = anak,
    total = total,
    ibadahNama = ibadah.nm_ibadah,
    ibadahTgl = ibadah.tgl,
    ibadahWaktu = ibadah.waktu,
)

fun KeuanganDto.toEntity() = KeuanganEntity(
    id = id,
    kategori = kategori,
    nmPemberi = nm_pemberi,
    jumlah = jumlah,
    keterangan = keterangan,
    createdAt = created_at,
    ibadahNama = ibadah?.nm_ibadah,
    ibadahTgl = ibadah?.tgl,
    ibadahWaktu = ibadah?.waktu,
)
