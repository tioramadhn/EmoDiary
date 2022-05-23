package com.dicoding.emodiary.data.remote.response

import com.google.gson.annotations.SerializedName

data class ErrorMessageResponse(

	@field:SerializedName("message")
	val message: String? = null
)
