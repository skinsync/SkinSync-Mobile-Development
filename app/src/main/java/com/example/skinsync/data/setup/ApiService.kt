package com.example.skinsync.data.setup

import com.example.skinsync.data.articleadmin.ArticleAdminResponse
import com.example.skinsync.data.articleadmin.ArticleRequest
import com.example.skinsync.data.articleadmin.ArticlesResponse
import com.example.skinsync.data.auth.LoginRequest
import com.example.skinsync.data.auth.LoginResponse
import com.example.skinsync.data.auth.RegisterRequest
import com.example.skinsync.data.auth.RegisterResponse
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @POST("auth/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @GET("articles")
    suspend fun getArticles(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("sortBy") sortBy: String,
        @Query("order") order: String,
        @Query("search") search: String
    ): ArticleAdminResponse

    @POST("articles")
    fun postArticle(
        @Body articleRequest: ArticleRequest
    ): Call<ArticlesResponse>
}