package com.dicoding.emodiary.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.dicoding.emodiary.data.repository.Repository

class MainViewModel(private val repository: Repository) : ViewModel() {
    fun login(email: String, password: String) = repository.login(email, password)
    fun register(email: String, fullname: String, password: String) =
        repository.register(email, fullname, password)

    fun getDiaries(page: Int, size: Int) = repository.getDiaries(page, size)
}