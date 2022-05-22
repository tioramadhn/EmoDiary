package com.dicoding.emodiary.data.remote.retrofit

import retrofit2.http.Field
import retrofit2.http.POST
import com.dicoding.emodiary.data.remote.response.RefreshAccessTokenResponse

interface ApiService {
    @POST("/authentications/refresh")
    suspend fun refreshAccessToken(
        @Field("refreshToken") refreshToken: String
    ): RefreshAccessTokenResponse
}