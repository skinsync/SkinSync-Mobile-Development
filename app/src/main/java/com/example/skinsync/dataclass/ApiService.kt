package com.example.skinsync.dataclass

import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.Call

interface ApiService {
    @POST("auth/register")
    fun register(@Body request: RegisterRequest): Call<AuthResponse>

    @POST("auth/login")
    fun login(@Body request: LoginRequest): Call<AuthResponse>
}