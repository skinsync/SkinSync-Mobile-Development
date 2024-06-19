package com.example.skinsync.activity.users.scan.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.skinsync.data.UserModel
import com.example.skinsync.data.UserPreference
import com.example.skinsync.data.setup.ApiConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class RecommendationRepository(private val pref: UserPreference) {

    fun getRecommendedSkincare(
        skintype: String,
        productType: String,
        notableEffects: List<String>
    ): LiveData<PagingData<RecommendationDataItem>> = liveData {
        val token = pref.getSession().first().token
        val apiService = ApiConfig.getApiService(token)
        emitSource(
            Pager(
                config = PagingConfig(pageSize = 5, enablePlaceholders = false),
                pagingSourceFactory = {
                    RecommendationPagingSource(apiService, skintype, productType, notableEffects)
                }
            ).liveData
        )
    }

    fun getSession(): Flow<UserModel> {
        return pref.getSession()
    }

    companion object {
        @Volatile
        private var instance: RecommendationRepository? = null
        fun getInstance(pref: UserPreference): RecommendationRepository =
            instance ?: synchronized(this) {
                instance ?: RecommendationRepository(pref)
            }.also { instance = it }
    }
}