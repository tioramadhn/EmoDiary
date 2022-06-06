package com.dicoding.emodiary.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.dicoding.emodiary.data.remote.body.RegisterBody
import com.dicoding.emodiary.data.repository.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {
    fun login(email: String, password: String) = repository.login(email, password)
    fun register(registerBody: RegisterBody) = repository.register(registerBody)
    fun getDiaries() = repository.getDiaries()
}