package com.dicoding.emodiary.data.remote.retrofit

import android.content.Context
import com.dicoding.emodiary.utils.ACCESS_TOKEN
import com.dicoding.emodiary.utils.SessionManager
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

// this class will triggered when there is a 401 error
class TokenAuthenticator(context: Context): Authenticator {
    private val sessionManager = SessionManager(context)

    @OptIn(InternalCoroutinesApi::class)
    override fun authenticate(route: Route?, response: Response): Request? {

        return null
    }
}