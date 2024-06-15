package com.example.skinsync.activity.users.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    suspend fun editMyProfile(
        name: String,
        password: String,
        email: String,
        gender: String,
        birthdate: String,
        profilePictureFile: File?
    ): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        val token = pref.getSession().first().token
        val apiService = ApiConfig.getApiService(token)

        withContext(Dispatchers.IO) {
            try {
                Log.d("ProfileRepository", "Username: $name")
                Log.d("ProfileRepository", "Selected Date: $birthdate")
                Log.d("ProfileRepository", "Gender: $gender")
                Log.d("ProfileRepository", "Email: $email")
                Log.d("ProfileRepository", "Password: $password")
                // Ensure email is not empty
                if (email.isEmpty()) {
                    Log.e("ProfileRepository", "Email is required but is empty")
                    result.postValue(false)
                    return@withContext
                }
//
//                val profilePicturePart = profilePictureFile?.let {
//                    val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), it)
//                    MultipartBody.Part.createFormData("profile_picture", it.name, requestFile)
//                }

                val profilePicturePart = MultipartBody.Part.createFormData(
                    "profile_picture",
                    profilePictureFile!!.name,
                    profilePictureFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                )

                // Create RequestBody for other fields
                val namePart = RequestBody.create("text/plain".toMediaTypeOrNull(), name)
                val emailPart = RequestBody.create("text/plain".toMediaTypeOrNull(), email)
                val genderPart = RequestBody.create("text/plain".toMediaTypeOrNull(), gender)
                val birthdatePart = RequestBody.create("text/plain".toMediaTypeOrNull(), birthdate)
                val passwordPart = RequestBody.create("text/plain".toMediaTypeOrNull(), password)

                Log.e("ProfileRepository", "Profile Picture: ${profilePictureFile.name}")
                apiService.editMyProfile(
                    namePart,
                    passwordPart,
                    emailPart,
                    genderPart,
                    birthdatePart,
                    profilePicturePart
                ).enqueue(object : Callback<EditProfileResponse> {
                    override fun onResponse(call: Call<EditProfileResponse>, response: Response<EditProfileResponse>) {
                        Log.e("ProfileRepository", "Updating profile: ${response.message()}")
                        result.postValue(true)
                    }

                    override fun onFailure(call: Call<EditProfileResponse>, t: Throwable) {
                        Log.e("ProfileRepository", "Error updating profile: ${t.message}")
                        result.postValue(false)
                    }
                })

//                Log.d("ProfileRepository", "Response code: ${response.code()}")
//                Log.d("ProfileRepository", "Response body: ${response.body()}")
//                Log.d("ProfileRepository", "Response error body: ${response.errorBody()?.string()}")
//
//                result.postValue(response.isSuccessful)
//                Log.i("ProfileRepository", "Profile update response: ${response.isSuccessful}")
            } catch (e: Exception) {
                Log.e("ProfileRepository", "Error updating profile: ${e.message}")
                result.postValue(false)
            }
        }

        return result
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