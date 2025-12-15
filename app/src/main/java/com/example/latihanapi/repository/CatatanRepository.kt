package com.example.latihanapi.repository

import com.example.latihanapi.entities.Catatan
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE // <--- Pastikan ini di-import
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CatatanRepository {
    @POST("catatans")
    suspend fun createCatatan(@Body catatan: Catatan): Response<Catatan>

    @GET("catatans")
    suspend fun getCatatan(): Response<List<Catatan>>

    @GET("catatans/{id}")
    suspend fun getCatatan(@Path("id") id: Int): Response<Catatan>

    @PUT("catatans/{id}")
    suspend fun editCatatan(@Path("id") id: Int, @Body catatan: Catatan): Response<Catatan>

    // --- PASTIIN BAGIAN INI ADA ---
    @DELETE("catatans/{id}")
    suspend fun deleteCatatan(@Path("id") id: Int): Response<Void>
}