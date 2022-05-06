package com.dicoding.emodiary.utils

import android.content.Context

class SessionManager(context: Context) {
    private var pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private var editor = pref.edit()

    fun setToken(token: String) {
        editor.apply {
            putString(PREF_TOKEN_KEY, token)
            apply()
        }
    }

    // if token exists { user logged-in } else { user log-out }
    fun getToken() = pref.getString(PREF_TOKEN_KEY, "").toString()

    fun clearSession() {
        editor.apply {
            clear()
            apply()
        }
    }
}