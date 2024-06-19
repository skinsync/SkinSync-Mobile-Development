package com.example.skinsync.data

data class UserModel(
    val email: String,
    val token: String,
    val role: String,
    val isLogin: Boolean = false,
    val skinType: String
)