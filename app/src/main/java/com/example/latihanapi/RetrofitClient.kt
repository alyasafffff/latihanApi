package com.example.latihanapi

import com.example.latihanapi.repository.CatatanRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // Pastikan IP ini benar sesuai laptop kamu saat ini
    private const val BASE_URL = "http://192.168.6.71:8000/api/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        // --- TAMBAHKAN BAGIAN INI ---
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Accept", "application/json") // Header wajib untuk Laravel API
                .build()
            chain.proceed(request)
        }
        // -----------------------------
        .build()

    val catatanRepository: CatatanRepository by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatatanRepository::class.java)
    }
}