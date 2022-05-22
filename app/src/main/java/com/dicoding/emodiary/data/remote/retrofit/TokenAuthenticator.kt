package com.dicoding.emodiary.data.remote.retrofit

import android.content.Context
import com.dicoding.emodiary.di.Injection
import com.dicoding.emodiary.utils.ACCESS_TOKEN
import com.dicoding.emodiary.utils.REFRESH_TOKEN
import com.dicoding.emodiary.utils.SessionManager
import com.dicoding.emodiary.utils.State
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.net.HttpURLConnection

// this class will triggered when there is a 401 error
class TokenAuthenticator(context: Context) : Authenticator {
    private val sessionManager = SessionManager(context)
    private val repository = Injection.provideRepository(context)

    @OptIn(InternalCoroutinesApi::class)
    override fun authenticate(route: Route?, response: Response): Request? {
        synchronized(this) {
            val refreshToken = sessionManager.getString(REFRESH_TOKEN)
            repository.refreshAccessToken(refreshToken).value?.let {
                return when (it) {
                    is State.Success -> {
                        val accessToken = it.data.data?.accessToken
                        sessionManager.setString(ACCESS_TOKEN, accessToken ?: "")
                        return response.request
                            .newBuilder()
                            .header("Authorization", "Bearer $accessToken")
                            .build()
                    }
                    else -> null
                }
            }
            return null
        }
    }
}