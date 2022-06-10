package com.dicoding.emodiary.data.remote.body

data class UpdateProfileBody(
    val email: String? = null,
    val phone: String? = null,
    val currentPassword: String? = null,
    val newPassword: String? = null
)