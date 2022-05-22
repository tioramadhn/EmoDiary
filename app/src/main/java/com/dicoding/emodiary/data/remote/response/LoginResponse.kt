package com.dicoding.emodiary.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("access_token")
	val accessToken: String? = null,

	@field:SerializedName("refresh_token")
	val refreshToken: String? = null,

	@field:SerializedName("scope")
	val scope: String? = null,

	@field:SerializedName("token_type")
	val tokenType: String? = null,

	@field:SerializedName("expires_in")
	val expiresIn: Int? = null,

	@field:SerializedName("user")
	val user: User? = null
)

data class User(

	@field:SerializedName("role")
	val role: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("timeUpdated")
	val timeUpdated: String? = null,

	@field:SerializedName("photo")
	val photo: String? = null,

	@field:SerializedName("timeCreated")
	val timeCreated: String? = null,

	@field:SerializedName("fullname")
	val fullname: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("isActive")
	val isActive: Boolean? = null,

	@field:SerializedName("email")
	val email: String? = null
)
