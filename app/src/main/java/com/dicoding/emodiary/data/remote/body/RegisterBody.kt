package com.dicoding.emodiary.data.remote.body

data class RegisterBody(
    val email: String,
    val fullname: String,
    val password: String
)
