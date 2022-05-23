package com.dicoding.emodiary.data.remote.retrofit

import com.dicoding.emodiary.data.remote.body.RefreshTokenBody
import com.dicoding.emodiary.data.remote.response.DiariesResponse
import com.dicoding.emodiary.data.remote.response.LoginResponse
import com.dicoding.emodiary.data.remote.response.RefreshAccessTokenResponse
import com.dicoding.emodiary.data.remote.response.RegisterResponse
import retrofit2.http.*

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
        @Body refreshToken: RefreshTokenBody
    ): RefreshAccessTokenResponse

    @GET("/diaries")
    suspend fun getDiaries(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): DiariesResponse
}