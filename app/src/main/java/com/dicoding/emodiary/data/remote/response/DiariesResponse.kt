package com.dicoding.emodiary.data.remote.response

import com.google.gson.annotations.SerializedName

data class DiariesResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: List<DiaryItem?>? = null,
)