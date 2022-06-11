package com.dicoding.emodiary.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.dicoding.emodiary.data.local.datastore.SettingPreferences
import com.dicoding.emodiary.data.remote.retrofit.ApiConfig
import com.dicoding.emodiary.data.repository.Repository
import com.dicoding.emodiary.data.repository.SettingRepository

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService(context)
        return Repository.getInstance(apiService)
    }

    fun provideSettingRepository(dataStore: DataStore<Preferences>): SettingRepository {
        val settingPreferences = SettingPreferences.getInstance(dataStore)
        return SettingRepository.getInstance(settingPreferences)
    }
}