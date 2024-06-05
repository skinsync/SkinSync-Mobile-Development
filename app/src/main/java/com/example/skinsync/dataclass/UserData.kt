package com.example.skinsync.dataclass

import android.net.Uri

data class UserData(
    val name: String?,
    val email: String?,
    val password: String,
    val birthdate: Int,
    val gender: String,
    val role: String,
    val profile_picture: String
)