package com.dicoding.emodiary.data.remote.retrofit

import retrofit2.http.Field
import retrofit2.http.POST

interface ApiService {
    @POST("/authentications/refresh")
    suspend fun refreshToken(
        @Field("refreshToken") refreshToken: String
    ): String
}