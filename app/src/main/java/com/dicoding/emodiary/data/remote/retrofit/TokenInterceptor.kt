package com.dicoding.emodiary.data.remote.retrofit

import android.content.Context
import com.dicoding.emodiary.utils.ACCESS_TOKEN
import com.dicoding.emodiary.utils.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(context: Context) : Interceptor {
    private val sessionManager = SessionManager(context)

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = sessionManager.getString(ACCESS_TOKEN)

        return if (accessToken.isEmpty()) {
            chain.proceed(chain.request())
        } else {
            val request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
            chain.proceed(request)
        }
    }
}