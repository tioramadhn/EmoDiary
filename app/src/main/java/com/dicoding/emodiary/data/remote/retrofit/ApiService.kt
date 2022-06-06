package com.dicoding.emodiary.data.remote.retrofit

import com.dicoding.emodiary.data.remote.body.CreateDiaryBody
import com.dicoding.emodiary.data.remote.body.RefreshTokenBody
import com.dicoding.emodiary.data.remote.body.RegisterBody
import com.dicoding.emodiary.data.remote.response.*
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("/authentications/login")
    suspend fun login(
        @Field("username") email: String,
        @Field("password") password: String
    ): LoginResponse

    @POST("/users")
    suspend fun register(
        @Body registerBody: RegisterBody
    ): RegisterResponse

    @POST("/authentications/refresh")
    suspend fun refreshAccessToken(
        @Body refreshToken: RefreshTokenBody
    ): RefreshAccessTokenResponse

    @GET("/diaries")
    suspend fun getDiaries(): DiariesResponse

    @POST("/diaries")
    suspend fun createDiary(
        @Body createDiaryBody: CreateDiaryBody
    ): DiaryResponse

    @GET("/diaries/{id}")
    suspend fun getDiary(
        @Path("id") id: String
    ): DiaryResponse

    @DELETE("/diaries/{id}")
    suspend fun deleteDiary(
        @Path("id") id: String
    ): DiaryResponse

    @PATCH("/diaries/{id}")
    suspend fun updateDiary(
        @Path("id") id: String,
        @Body updateDiaryBody: CreateDiaryBody
    ): DiaryResponse

    @GET("/articles")
    suspend fun getArticles(
        @Query("emotions") emotions: List<String>
    ): ArticlesResponse
}