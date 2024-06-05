package com.example.skinsync.dataclass

data class UserModel(
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)