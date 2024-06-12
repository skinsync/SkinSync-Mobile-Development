package com.example.skinsync.data.articleuser

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
)

data class ArticleUserResponse(
    @SerializedName("articles") val articles: List<Article>,
    @SerializedName("total") val total: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("limit") val limit: Int
)