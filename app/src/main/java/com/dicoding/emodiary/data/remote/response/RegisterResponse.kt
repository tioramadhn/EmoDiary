package com.dicoding.emodiary.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class Data(

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
