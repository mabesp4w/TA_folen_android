package com.example.appandroid.data.remote.dto

import com.example.appandroid.data.local.entity.AlbumEntity
import com.example.appandroid.data.local.entity.JadwalEntity
import com.example.appandroid.data.local.entity.PengumumanEntity
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
