package com.dicoding.emodiary.data.remote.response

import com.google.gson.annotations.SerializedName

data class RefreshAccessTokenItem(

    @field:SerializedName("accessToken")
    val accessToken: String? = null
)
