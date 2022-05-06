package com.dicoding.emodiary.data.repository

import androidx.lifecycle.liveData
import com.dicoding.emodiary.R
import com.dicoding.emodiary.utils.State
import org.json.JSONObject
import retrofit2.HttpException
import java.net.SocketException
import java.net.UnknownHostException

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
                    val nullableMessage = "{'message': '${R.string.network_error}'}"
                    val errorBody = JSONObject(throwable.response()?.errorBody()?.toString() ?: nullableMessage )
                    emit(State.Error(errorBody.getString("message")))
                }
                is SocketException -> emit(State.Error(message))
                is UnknownHostException -> emit(State.Error(message))
                else -> emit(State.Error(message))
            }
        }
    }
}