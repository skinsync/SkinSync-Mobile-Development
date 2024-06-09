package com.example.skinsync.data.auth

data class RegisterRequest(
    val name: String,
    val password: String,
    val email: String)