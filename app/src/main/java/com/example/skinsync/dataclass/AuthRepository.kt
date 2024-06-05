package com.example.skinsync.dataclass

import kotlinx.coroutines.flow.Flow

class AuthRepository private constructor(
    private val userPreference: UserPreference
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }
    private val authService: ApiService = ApiConfig.getApiService()

    fun register(request: RegisterRequest, callback: (AuthResponse?, Throwable?) -> Unit) {
        authService.register(request).enqueue(object : retrofit2.Callback<AuthResponse> {
            override fun onResponse(call: retrofit2.Call<AuthResponse>, response: retrofit2.Response<AuthResponse>) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    callback(null, Throwable(response.message()))
                }
            }

            override fun onFailure(call: retrofit2.Call<AuthResponse>, t: Throwable) {
                callback(null, t)
            }
        })
    }

    fun login(request: LoginRequest, callback: (AuthResponse?, Throwable?) -> Unit) {
        authService.login(request).enqueue(object : retrofit2.Callback<AuthResponse> {
            override fun onResponse(call: retrofit2.Call<AuthResponse>, response: retrofit2.Response<AuthResponse>) {
                if (response.isSuccessful) {
                    callback(response.body(), null)
                } else {
                    callback(null, Throwable(response.message()))
                }
            }

            override fun onFailure(call: retrofit2.Call<AuthResponse>, t: Throwable) {
                callback(null, t)
            }
        })
    }

    companion object {
        @Volatile
        private var instance: AuthRepository? = null
        fun getInstance(
            userPreference: UserPreference
        ): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(userPreference)
            }.also { instance = it }
    }
}