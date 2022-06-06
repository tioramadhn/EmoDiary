package com.dicoding.emodiary.data.remote.response

import com.google.gson.annotations.SerializedName

data class BaseResponse <T>(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: T? = null,
)
