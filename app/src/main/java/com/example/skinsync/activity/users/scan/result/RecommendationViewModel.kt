package com.example.skinsync.activity.users.scan.result

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skinsync.activity.users.listproduct.ProductDataItem
import com.example.skinsync.data.UserModel
import kotlinx.coroutines.launch

class RecommendationViewModel(private val repository: RecommendationRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _recommendedSkincare = MutableLiveData<PagingData<RecommendationDataItem>>()
    val recommendedSkincare: LiveData<PagingData<RecommendationDataItem>> get() = _recommendedSkincare

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun getRecommendedSkincare(
        skintype: String,
        productType: String,
        notableEffects: List<String>
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getRecommendedSkincare(skintype, productType, notableEffects)
                .cachedIn(viewModelScope)
                .observeForever {
                    _recommendedSkincare.value = it
                    Log.d("Cek Nilainya ada", "${_recommendedSkincare.value}")
                    _isLoading.value = false
                }
        }
    }
}