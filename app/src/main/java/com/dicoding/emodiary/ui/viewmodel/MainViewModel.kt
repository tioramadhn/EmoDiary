package com.dicoding.emodiary.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.dicoding.emodiary.data.remote.body.CreateDiaryBody
import com.dicoding.emodiary.data.remote.body.RegisterBody
import com.dicoding.emodiary.data.repository.Repository
import okhttp3.MultipartBody

class MainViewModel(private val repository: Repository) : ViewModel() {
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
    fun uploadPhoto(id: String, File: MultipartBody.Part) = repository.uploadPhoto(id, File)
}