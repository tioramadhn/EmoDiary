package com.dicoding.emodiary.data.repository

import com.dicoding.emodiary.data.remote.body.RegisterBody
import com.dicoding.emodiary.data.remote.retrofit.ApiService

class Repository(private val apiService: ApiService) : BaseRepository() {

    fun login(email: String, password: String) = safeApiCall {
        apiService.login(email, password)
    }

    fun register(registerBody: RegisterBody) = safeApiCall {
        apiService.register(registerBody)
    }

    fun getDiaries(page: Int, size: Int) = safeApiCall {
        apiService.getDiaries(page, size)
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