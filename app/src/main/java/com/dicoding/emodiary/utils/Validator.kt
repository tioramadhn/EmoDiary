package com.dicoding.emodiary.utils

import android.content.Context
import android.util.Patterns
import com.dicoding.emodiary.R

fun isEmailValid(context: Context, email: String): Pair<String, Boolean> {
    return when {
        email.isEmpty() -> context.getString(R.string.email_required_error) to false
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> context.getString(R.string.email_invalid_error) to false
        else -> email to true
    }
}

fun isPasswordValid(context: Context, password: String): Pair<String, Boolean> {
    return when {
        password.isEmpty() -> context.getString(R.string.password_required_error) to false
        password.length < 6 -> context.getString(R.string.password_length_error) to false
        password.contains(" ") -> context.getString(R.string.password_white_space_error) to false
        !password.contains(Regex("[A-Z]")) -> context.getString(R.string.password_upper_case_error) to false
        !password.contains(Regex("[a-z]")) -> context.getString(R.string.password_lower_case_error) to false
        !password.contains(Regex("[0-9]")) -> context.getString(R.string.password_number_error) to false
        else -> password to true
    }
}
