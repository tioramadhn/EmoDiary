package com.dicoding.emodiary.data.remote.response
import com.google.gson.annotations.SerializedName

data class EmotionItem(

    @field:SerializedName("emotion")
    val emotion: String? = null
)
