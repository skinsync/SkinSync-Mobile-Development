package com.example.skinsync.activity.users.profile

data class ProfileResponse(
	val data: Data? = null
)

data class Data(
	val password: String? = null,
	val birthdate: Any? = null,
	val gender: Any? = null,
	val name: String? = null,
	val profilePicture: Any? = null,
	val id: Int? = null,
	val email: String? = null
)

