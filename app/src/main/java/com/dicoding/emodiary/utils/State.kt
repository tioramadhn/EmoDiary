package com.dicoding.emodiary.utils

sealed class State<out R> private constructor() {
    data class Success<out T>(val data: T) : State<T>()
    data class Error(val error: String) : State<Nothing>()
    object Loading : State<Nothing>()
}