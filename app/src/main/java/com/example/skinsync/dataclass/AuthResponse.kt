package com.example.skinsync.dataclass

data class AuthResponse(
    val data: UserData,
    val token: String,
    val message: String)