package com.example.latihanapi.repository

import com.example.latihanapi.entities.Catatan
import retrofit2.http.Body
import retrofit2.http.POST

interface CatatanRepository {
    @POST("catatans")
    suspend fun createCatatan(@Body catatan: Catatan): Result<Catatan>
}