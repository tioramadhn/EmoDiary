package com.dicoding.emodiary.utils

import android.content.Context

class SessionManager(context: Context) {
    private var pref = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    private var editor = pref.edit()

    fun setString(key: String, value: String) {
        editor.apply {
            putString(key, value)
            apply()
        }
    }

    fun setBoolean(key: String, value: Boolean) {
        editor.apply {
            putBoolean(key, value)
            apply()
        }
    }

    // if token exists { user logged-in } else { user log-out }
    fun getString(key: String) = pref.getString(key, "").toString()

    fun getBoolean(key: String) = pref.getBoolean(key, false)

    fun clearSession() {
        editor.apply {
            clear()
            apply()
        }
    }
}