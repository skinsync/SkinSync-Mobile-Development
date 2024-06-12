package com.example.skinsync.data.articleadmin

data class ArticlesResponse(
	val data: Data? = null,
	val message: String? = null
)

data class Data(
	val createdAt: String? = null,
	val id: Int? = null,
	val title: String? = null,
	val picture: String? = null,
	val url: String? = null,
	val updatedAt: String? = null
)

