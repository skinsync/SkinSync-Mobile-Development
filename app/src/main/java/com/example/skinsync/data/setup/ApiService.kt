package com.example.skinsync.data.setup

import com.example.skinsync.data.articleadmin.ArticleAdminResponse
import com.example.skinsync.data.articleadmin.ArticleRequest
import com.example.skinsync.data.articleadmin.ArticlesResponse
import com.example.skinsync.activity.users.article.ArticleUserResponse
import com.example.skinsync.activity.users.profile.EditProfileResponse
import com.example.skinsync.activity.users.profile.ProfileResponse
import com.example.skinsync.data.auth.LoginRequest
import com.example.skinsync.data.auth.LoginResponse
import com.example.skinsync.data.auth.RegisterRequest
import com.example.skinsync.data.auth.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part
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

    @GET("articles")
    suspend fun getUserArticles(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("sortBy") sortBy: String,
        @Query("order") order: String,
        @Query("search") search: String
    ): Call<ArticleUserResponse>

    @GET("profile")
    suspend fun getMyProfile(): ProfileResponse

    @PUT("profile/edit")
    @Multipart
    suspend fun editMyProfile(
        @Part("name") name: RequestBody?,
        @Part("password") password: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("gender") gender: RequestBody?,
        @Part("birthdate") birthdate: RequestBody?,
        @Part profile_picture: MultipartBody.Part
    ): Call<EditProfileResponse>

}