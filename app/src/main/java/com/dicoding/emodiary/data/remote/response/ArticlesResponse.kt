package com.dicoding.emodiary.data.remote.response

import com.google.gson.annotations.SerializedName

data class ArticlesResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("data")
	val data: List<ArticlesItem?>? = null,
)

data class ArticlesItem(

	@field:SerializedName("snippet")
	val snippet: String? = null,

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("displayedLink")
	val displayedLink: String? = null,

	@field:SerializedName("emotion")
	val emotion: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("language")
	val language: String? = null,

	@field:SerializedName("position")
	val position: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("snippetHighlightedWords")
	val snippetHighlightedWords: List<String?>? = null,

	@field:SerializedName("richSnippet")
	val richSnippet: RichSnippet? = null
)

data class RichSnippet(

	@field:SerializedName("top")
	val top: Top? = null
)

data class Top(

	@field:SerializedName("extensions")
	val extensions: List<String?>? = null,

	@field:SerializedName("detectedExtensions")
	val detectedExtensions: DetectedExtensions? = null
)

data class DetectedExtensions(

	@field:SerializedName("langkah")
	val langkah: Int? = null,

	@field:SerializedName("suara")
	val suara: Int? = null,

	@field:SerializedName("skor")
	val skor: Int? = null
)





