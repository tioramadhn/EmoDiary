package com.dicoding.emodiary.data.remote.response

data class RefreshAccessTokenResponse(
    val message: String? = null,
    val data: Data? = null
)

data class Data(
    val accessToken: String? = null
)

