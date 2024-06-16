package com.example.skinsync.activity.users.scan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.skinsync.activity.users.profile.EditProfileResponse
import com.example.skinsync.activity.users.profile.ProfileRepository
import com.example.skinsync.activity.users.profile.ProfileResponse
import com.example.skinsync.data.UserPreference
import com.example.skinsync.data.setup.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PredictRepository (private val pref: UserPreference
) {

    companion object {
        @Volatile
        private var instance: PredictRepository? = null
        fun getInstance(pref: UserPreference): PredictRepository =
            instance ?: synchronized(this) {
                instance ?: PredictRepository(pref)
            }.also { instance = it }
    }

}