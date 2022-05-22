package com.dicoding.emodiary.utils

import android.content.Context

class SessionManager(context: Context) {
    private var pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private var editor = pref.edit()

    fun saveString(key: String, token: String) {
        editor.apply {
            putString(key, token)
            apply()
        }
    }

    // if token exists { user logged-in } else { user log-out }
    fun getString(key: String) = pref.getString(key, "").toString()

    fun clearSession() {
        editor.apply {
            clear()
            apply()
        }
    }
}