package com.dicoding.emodiary.ui.viewmodel

import android.content.Context
import androidx.lifecycle.*
import com.dicoding.emodiary.data.remote.body.CreateDiaryBody
import com.dicoding.emodiary.data.remote.body.RegisterBody
import com.dicoding.emodiary.data.remote.body.UpdateProfileBody
import com.dicoding.emodiary.data.repository.Repository
import com.dicoding.emodiary.data.repository.SettingRepository
import com.dicoding.emodiary.utils.EMAIL
import com.dicoding.emodiary.utils.FULL_NAME
import com.dicoding.emodiary.utils.SessionManager
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class MainViewModel(private val repository: Repository, private val settingRepository: SettingRepository) : ViewModel() {
    fun login(email: String, password: String) = repository.login(email, password)
    fun loginWithGoogle(credential: String) = repository.loginWithGoogle(credential)
    fun register(registerBody: RegisterBody) = repository.register(registerBody)
    fun getDiaries() = repository.getDiaries()
    fun deleteDiary(id: String) = repository.deleteDiary(id)
    fun createDiary(diaryBody: CreateDiaryBody) = repository.createDiary(diaryBody)
    fun getDiary(id: String) = repository.getDiary(id)
    fun updateDiary(id: String, diaryBody: CreateDiaryBody) = repository.updateDiary(id, diaryBody)
    fun getEmotions() = repository.getEmotions()
    fun getArticles(emotions: List<String> ) = repository.getArticles(emotions)
    fun uploadPhoto(id: String, file: MultipartBody.Part) = repository.uploadPhoto(id, file)
    fun getThemeSetting(): LiveData<Boolean> = settingRepository.getThemeSetting().asLiveData()
    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            with(settingRepository) { this.saveThemeSetting(isDarkModeActive) }
        }
    }
    fun updateProfile(id: String, userProfileBody: UpdateProfileBody) = repository.updateProfile(id, userProfileBody)



}