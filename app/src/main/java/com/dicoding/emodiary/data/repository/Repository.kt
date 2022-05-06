package com.dicoding.emodiary.data.repository

import com.dicoding.emodiary.data.remote.retrofit.ApiService

class Repository(private val apiService: ApiService) : BaseRepository() {

    /*
     *
     *   Example implementation
     *
     *   fun login(email: String, password: String) = safeApiCall {
     *      apiService.login(email, password)
     *   }
     *
     */

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(apiService: ApiService): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService)
            }.also { instance = it }
    }
}