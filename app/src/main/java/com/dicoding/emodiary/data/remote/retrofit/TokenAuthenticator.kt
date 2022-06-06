package com.dicoding.emodiary.data.remote.retrofit

import android.content.Context
import android.util.Log
import com.dicoding.emodiary.R
import com.dicoding.emodiary.data.remote.body.RefreshTokenBody
import com.dicoding.emodiary.data.remote.response.BaseResponse
import com.dicoding.emodiary.data.remote.response.ErrorMessageItem
import com.dicoding.emodiary.data.remote.response.RefreshAccessTokenItem
import com.dicoding.emodiary.data.remote.retrofit.ApiConfig.BASE_URL
import com.dicoding.emodiary.utils.ACCESS_TOKEN
import com.dicoding.emodiary.utils.REFRESH_TOKEN
import com.dicoding.emodiary.utils.SessionManager
import com.dicoding.emodiary.utils.State
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.*
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// this class will triggered when there is a 401 unauthorized error
class TokenAuthenticator(context: Context) : Authenticator {
    private val sessionManager = SessionManager(context)

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            when (val tokenResponse = refreshAccessToken()) {
                is State.Success -> {
                    Log.d("TokenAuthenticator", "refresh access token success")
                    val newAccessToken = tokenResponse.data.data?.accessToken.toString()
                    sessionManager.setString(ACCESS_TOKEN, newAccessToken)
                    response.request.newBuilder()
                        .header("Authorization", "Bearer $newAccessToken")
                        .build()
                }
                else -> null
            }
        }
    }

    private suspend fun refreshAccessToken(): State<BaseResponse<RefreshAccessTokenItem?>> {
        val refreshToken = sessionManager.getString(REFRESH_TOKEN)
        val refreshTokenBody = RefreshTokenBody(refreshToken)

        return withContext(Dispatchers.IO) {
            try {
                val client = OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
                    .create(ApiService::class.java)

                val response = retrofit.refreshAccessToken(refreshTokenBody)
                State.Success(response)

            } catch (throwable: Throwable) {
                val message = throwable.message.toString()

                when (throwable) {
                    is HttpException -> {
                        val error = Gson().fromJson(
                            throwable.response()?.errorBody()?.string(),
                            ErrorMessageItem::class.java
                        )
                        State.Error(error.message ?: R.string.network_error.toString())
                    }
                    else -> State.Error(message)
                }
            }
        }
    }
}