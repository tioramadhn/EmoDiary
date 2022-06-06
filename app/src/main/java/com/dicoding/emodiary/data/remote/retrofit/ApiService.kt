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
    ): BaseResponse<RegisterItem>

    @POST("/authentications/refresh")
    suspend fun refreshAccessToken(
        @Body refreshToken: RefreshTokenBody
    ): BaseResponse<RefreshAccessTokenItem?>

    @GET("/diaries")
    suspend fun getDiaries(): BaseResponse<List<DiaryItem?>>

    @POST("/diaries")
    suspend fun createDiary(
        @Body createDiaryBody: CreateDiaryBody
    ): BaseResponse<DiaryItem?>

    @GET("diaries/emotions")
    suspend fun getEmotions(): BaseResponse<EmotionItem?>

    @GET("/diaries/{id}")
    suspend fun getDiary(
        @Path("id") id: String
    ): BaseResponse<DiaryItem?>

    @DELETE("/diaries/{id}")
    suspend fun deleteDiary(
        @Path("id") id: String
    ): BaseResponse<DiaryItem?>

    @PATCH("/diaries/{id}")
    suspend fun updateDiary(
        @Path("id") id: String,
        @Body updateDiaryBody: CreateDiaryBody
    ): BaseResponse<DiaryItem?>

    @GET("/articles")
    suspend fun getArticles(
        @Query("emotions") emotions: List<String>
    ): BaseResponse<ArticleItem?>
}