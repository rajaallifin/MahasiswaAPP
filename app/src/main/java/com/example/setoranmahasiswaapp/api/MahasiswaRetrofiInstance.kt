package com.example.setoranmahasiswaapp.api

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object MahasiswaRetrofiInstance {

    private const val BASE_URL = "https://api.tif.uin-suska.ac.id/"

    private fun provideOkHttpClient(context: Context): OkHttpClient {
        // grab the same prefs you wrote the token into
        val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(prefs))
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // you’ll need to pass a Context in here, e.g. from your Application subclass
    fun create(context: Context): MahasiswaApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MahasiswaApiService::class.java)
    }

}