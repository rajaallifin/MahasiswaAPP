package com.example.setoranmahasiswaapp.api

import com.example.setoranmahasiswaapp.model.Mahasiswa
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming

interface MahasiswaApiService {

    @GET("setoran-dev/v1/mahasiswa/setoran-saya")
    fun mahasiswaSetoran(): Call<Mahasiswa>

    @Streaming
    @GET("setoran-dev/v1/mahasiswa/kartu-murojaah-saya")
    fun downloadetoran(): Call<ResponseBody>

}