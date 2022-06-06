package com.dicoding.emodiary.data.repository

import androidx.lifecycle.liveData
import com.dicoding.emodiary.R
import com.dicoding.emodiary.data.remote.response.ErrorMessageItem
import com.dicoding.emodiary.utils.State
import com.google.gson.Gson
import retrofit2.HttpException

open class BaseRepository {
    fun <T> safeApiCall(callback: suspend () -> T) = liveData {
        emit(State.Loading)
        try {
            val response = callback()    // blocking block
            emit(State.Success(response))
        } catch (throwable: Throwable) {
            val message = throwable.message.toString()

            when (throwable) {
                is HttpException -> {
                    val error = Gson().fromJson(
                        throwable.response()?.errorBody()?.string(),
                        ErrorMessageItem::class.java
                    )
                    emit(State.Error(error.message ?: R.string.network_error.toString()))
                }
                else -> emit(State.Error(message))
            }
        }
    }
}