package com.dicoding.emodiary.data.remote.response

import com.google.gson.annotations.SerializedName

data class DiaryResponse(
	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("data")
	val data: DiaryItem? = null,
)
