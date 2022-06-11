package com.dicoding.emodiary.data.repository

import com.dicoding.emodiary.data.local.datastore.SettingPreferences
import kotlinx.coroutines.flow.Flow

class SettingRepository private constructor(private val settingPreferences: SettingPreferences) {

    fun getThemeSetting(): Flow<Boolean> = settingPreferences.getThemeSetting()

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        settingPreferences.saveThemeSetting(isDarkModeActive)
    }

    companion object {
        @Volatile
        private var instance: SettingRepository? = null
        fun getInstance(settingPreferences: SettingPreferences): SettingRepository =
            instance ?: synchronized(this) {
                instance ?: SettingRepository(settingPreferences)
            }.also { instance = it }
    }
}