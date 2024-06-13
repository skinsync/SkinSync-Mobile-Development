package com.example.skinsync.activity.users.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.skinsync.data.UserPreference
import com.example.skinsync.data.setup.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class ProfileRepository(private val pref: UserPreference
) {
    suspend fun getMyProfile(): LiveData<ProfileResponse?> {
        val data = MutableLiveData<ProfileResponse?>()
        val token = pref.getSession().first().token
        val apiService = ApiConfig.getApiService(token)
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getMyProfile()
                data.postValue(response)
                Log.i("ProfileRepository", "Profile data: $response")
            } catch (e: Exception) {
                Log.e("ProfileRepository", "Error fetching profile: ${e.message}")
                data.postValue(null)
            }
        }

        return data
    }
    companion object {
        @Volatile
        private var instance: ProfileRepository? = null
        fun getInstance(pref: UserPreference): ProfileRepository =
            instance ?: synchronized(this) {
                instance ?: ProfileRepository(pref)
            }.also { instance = it }
    }

}