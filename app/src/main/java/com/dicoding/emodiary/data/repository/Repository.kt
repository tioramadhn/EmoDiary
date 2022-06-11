package com.dicoding.emodiary.data.repository

import android.util.Log
import com.dicoding.emodiary.data.remote.body.CreateDiaryBody
import com.dicoding.emodiary.data.remote.body.RegisterBody
import com.dicoding.emodiary.data.remote.body.UpdateProfileBody
import com.dicoding.emodiary.data.remote.retrofit.ApiService
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.Url

class Repository(private val apiService: ApiService) : BaseRepository() {

    fun login(email: String, password: String) = safeApiCall {
        apiService.login(email, password)
    }

    fun loginWithGoogle(credential: String) = safeApiCall {
        apiService.loginWithGoogle(credential)
    }

    fun register(registerBody: RegisterBody) = safeApiCall {
        apiService.register(registerBody)
    }

    fun getDiaries() = safeApiCall {
        apiService.getDiaries()
    }

    fun createDiary(createDiaryBody: CreateDiaryBody) = safeApiCall {
        apiService.createDiary(createDiaryBody)
    }

    fun getEmotions() = safeApiCall {
        apiService.getEmotions()
    }

    fun getDiary(id: String) = safeApiCall {
        apiService.getDiary(id)
    }

    fun deleteDiary(id: String) = safeApiCall {
        apiService.deleteDiary(id)
    }

    fun updateDiary(id: String, createDiaryBody: CreateDiaryBody) = safeApiCall {
        apiService.updateDiary(id, createDiaryBody)
    }

    fun getArticles(emotions: List<String>) = safeApiCall {
        apiService.getArticles(emotions)
    }

    fun uploadPhoto(id: String, file: MultipartBody.Part) = safeApiCall {
        apiService.uploadPhoto(id, file)
    }

    fun updateProfile(id: String, userProfileBody: UpdateProfileBody) = safeApiCall {
        apiService.updateProfile(id, userProfileBody)
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(apiService: ApiService): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService)
            }.also { instance = it }
    }
}