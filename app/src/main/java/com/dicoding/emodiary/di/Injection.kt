package com.dicoding.emodiary.di

import android.content.Context
import com.dicoding.emodiary.data.remote.retrofit.ApiConfig
import com.dicoding.emodiary.data.repository.Repository

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService(context)
        return Repository.getInstance(apiService)
    }
}