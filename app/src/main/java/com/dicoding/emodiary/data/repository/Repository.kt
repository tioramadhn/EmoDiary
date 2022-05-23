package com.dicoding.emodiary.data.repository

import com.dicoding.emodiary.data.remote.retrofit.ApiService

class Repository(private val apiService: ApiService) : BaseRepository() {

    fun login(email: String, password: String) = safeApiCall {
        apiService.login(email, password)
    }

    fun register(email: String, fullname: String, password: String) = safeApiCall {
        apiService.register(email, fullname, password)
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