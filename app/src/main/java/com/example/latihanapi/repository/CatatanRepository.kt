package com.example.latihanapi.repository

import com.example.latihanapi.entities.Catatan
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CatatanRepository {
    @POST("catatans")
    suspend fun createCatatan(@Body catatan: Catatan): Response<Catatan> // Ubah Result ke Response agar lebih standar dengan Retrofit

    // TAMBAHKAN INI:
    @GET("catatans")
    suspend fun getCatatan(): Response<List<Catatan>>

    @GET("catatan/{id}")
    suspend fun getCatatan(@Path("id") id:Int): Response<Catatan>

    @PUT("catatans/{id}")
    suspend fun  editCatatan(@Path("id") id: Int, @Body catatan: Catatan): Response<Catatan>
}