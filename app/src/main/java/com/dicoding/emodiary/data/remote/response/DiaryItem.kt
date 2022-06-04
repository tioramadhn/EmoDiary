package com.dicoding.emodiary.data.remote.response

import com.google.gson.annotations.SerializedName

data class DiaryItem(

    @field:SerializedName("emotion")
    val emotion: String? = null,

    @field:SerializedName("timeUpdated")
    val timeUpdated: String? = null,

    @field:SerializedName("timeCreated")
    val timeCreated: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("translatedContent")
    val translatedContent: String? = null,

    @field:SerializedName("userId")
    val userId: String? = null,

    @field:SerializedName("content")
    val content: String? = null
)
