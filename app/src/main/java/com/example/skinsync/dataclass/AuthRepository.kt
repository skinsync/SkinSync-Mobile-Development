package com.example.skinsync.dataclass

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class AuthRepository private constructor(
    private val userPreference: UserPreference
) {

    private val authService: ApiService = ApiConfig.getApiService()

    private val authServiceToken: ApiService = ApiConfig.getApiService(runBlocking {
        userPreference.getSession().first().token
    })
    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

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