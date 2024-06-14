package com.example.skinsync.activity.users.profile.edit

data class EditProfileRequest(
    val name: String,
    val email: String,
    val gender: String,
    val birthdate: String,
    val profile_picture: String,
    val password: String
)