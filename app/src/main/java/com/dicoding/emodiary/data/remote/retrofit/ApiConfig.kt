package com.dicoding.emodiary.data.remote.retrofit

import android.content.Context
import androidx.viewbinding.BuildConfig
import com.dicoding.emodiary.utils.API_BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    var BASE_URL = API_BASE_URL

    fun getApiService(context: Context): ApiService {
        val level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(level)

        val client = OkHttpClient.Builder()
            .addInterceptor(InterceptorConfig(context))
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }
}