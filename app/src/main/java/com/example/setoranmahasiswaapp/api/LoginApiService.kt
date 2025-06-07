package com.example.setoranmahasiswaapp.api

import com.example.setoranmahasiswaapp.model.TokenResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApiService {
    @FormUrlEncoded
    @POST("realms/dev/protocol/openid-connect/token")
    fun login(
        @Field("client_id")
        clientId: String = "setoran-mobile-dev",
        @Field("client_secret")
        clientSecret: String = "aqJp3xnXKudgC7RMOshEQP7ZoVKWzoSl",
        @Field("grant_type")
        grantType: String = "password",
        @Field("username")
        username: String,
        @Field("password")
        password: String,
        @Field("scope")
        scope: String = "openid profile email"
    ): Call<TokenResponse>
}