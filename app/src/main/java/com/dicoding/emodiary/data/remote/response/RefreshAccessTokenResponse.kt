package com.dicoding.emodiary.data.remote.response

import com.google.gson.annotations.SerializedName

data class RefreshAccessTokenResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("data")
    val data: RefreshAccessTokenData? = null
)

data class RefreshAccessTokenData(
    val accessToken: String? = null
)

