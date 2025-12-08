package com.example.latihanapi.repository

import com.example.latihanapi.entities.Catatan
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CatatanRepository {
    @POST("catatans")
    suspend fun createCatatan(@Body catatan: Catatan): Response<Catatan> // Ubah Result ke Response agar lebih standar dengan Retrofit

    // TAMBAHKAN INI:
    @GET("catatans")
    suspend fun getCatatan(): Response<List<Catatan>>
}