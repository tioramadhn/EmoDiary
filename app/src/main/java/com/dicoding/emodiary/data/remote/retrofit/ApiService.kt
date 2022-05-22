package com.dicoding.emodiary.data.remote.retrofit

import com.dicoding.emodiary.data.remote.response.LoginResponse
import retrofit2.http.Field
import retrofit2.http.POST
import com.dicoding.emodiary.data.remote.response.RefreshAccessTokenResponse
import com.dicoding.emodiary.data.remote.response.RegisterResponse
import retrofit2.http.FormUrlEncoded

interface ApiService {
    @FormUrlEncoded
    @POST("/authentications/login")
    suspend fun login(
        @Field("username") email: String,
        @Field("password") password: String
    ): LoginResponse

    @FormUrlEncoded
    @POST("/authentications/users")
    suspend fun register(
        @Field("email") email: String,
        @Field("fullname") fullname: String,
        @Field("password") password: String
    ): RegisterResponse

    @POST("/authentications/refresh")
    suspend fun refreshAccessToken(
        @Field("refreshToken") refreshToken: String
    ): RefreshAccessTokenResponse
}