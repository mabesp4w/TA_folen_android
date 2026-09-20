package com.example.appandroid.data.remote

import com.example.appandroid.data.remote.dto.AlbumDetailDto
import com.example.appandroid.data.remote.dto.AlbumDto
import com.example.appandroid.data.remote.dto.ApiResponse
import com.example.appandroid.data.remote.dto.JadwalDto
import com.example.appandroid.data.remote.dto.PengumumanDto
import com.example.appandroid.data.remote.dto.RayonDto
import com.example.appandroid.data.remote.dto.RenunganDto
import com.example.appandroid.data.remote.dto.SlideDto
import com.example.appandroid.data.remote.dto.UnsurDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ChurchApiService {

    @GET("api/public/renungan/terbaru")
    suspend fun getRenunganTerbaru(): ApiResponse<RenunganDto>

    @GET("api/public/renungan")
    suspend fun getRenunganList(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
    ): ApiResponse<List<RenunganDto>>

    @GET("api/public/renungan/{id}")
    suspend fun getRenunganDetail(
        @Path("id") id: String,
    ): ApiResponse<RenunganDto>

    @GET("api/public/jadwal")
    suspend fun getJadwalList(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("dari") dari: String? = null,
        @Query("sampai") sampai: String? = null,
    ): ApiResponse<List<JadwalDto>>

    @GET("api/public/pengumuman")
    suspend fun getPengumumanList(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
    ): ApiResponse<List<PengumumanDto>>

    @GET("api/public/galeri")
    suspend fun getAlbumList(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
    ): ApiResponse<List<AlbumDto>>

    @GET("api/public/galeri/{id}")
    suspend fun getAlbumDetail(
        @Path("id") id: String,
    ): ApiResponse<AlbumDetailDto>

    @GET("api/public/slide")
    suspend fun getSlideList(): ApiResponse<List<SlideDto>>

    @GET("api/public/rayon")
    suspend fun getRayonList(): ApiResponse<List<RayonDto>>

    @GET("api/public/unsur")
    suspend fun getUnsurList(): ApiResponse<List<UnsurDto>>
}
