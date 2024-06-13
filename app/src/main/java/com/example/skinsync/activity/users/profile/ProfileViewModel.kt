package com.example.skinsync.activity.users.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skinsync.activity.users.article.ArticleUserRepository
import com.example.skinsync.data.articleadmin.ArticleData
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {
    private val _myProfile = MutableLiveData<ProfileResponse?>()
    val myProfile: LiveData<ProfileResponse?> get() = _myProfile

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchProfile() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val profileData = repository.getMyProfile().value
                _myProfile.value = profileData
                Log.i("ProfileViewModel", "Profile data fetched: $profileData")
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error fetching profile: ${e.message}")
                _myProfile.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }
}