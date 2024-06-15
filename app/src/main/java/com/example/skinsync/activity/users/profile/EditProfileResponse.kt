package com.example.skinsync.activity.users.profile

import com.google.gson.annotations.SerializedName

data class EditProfileResponse(

	@field:SerializedName("data")
	val data: DataEditProfile? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataEditProfile(

	@field:SerializedName("password")
	val password: String? = null,

	@field:SerializedName("birthdate")
	val birthdate: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("profile_picture")
	val profilePicture: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)
