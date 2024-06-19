package com.example.skinsync.activity.users.listproduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.skinsync.activity.users.article.ArticleUserRepository
import com.example.skinsync.data.UserModel
import com.example.skinsync.data.UserPreference
import com.example.skinsync.data.setup.ApiConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class ListProductRepository (private val pref: UserPreference
) {
    fun getProducts(): LiveData<PagingData<ProductDataItem>> = liveData {
        val token = pref.getSession().first().token
        val apiService = ApiConfig.getApiService(token)
        emitSource(
            Pager(
                config = PagingConfig(pageSize = 5, enablePlaceholders = false),
                pagingSourceFactory = { ListProductPagingSource(apiService) }
            ).liveData
        )
    }

    fun getSession(): Flow<UserModel> {
        return pref.getSession()
    }

    companion object {
        @Volatile
        private var instance: ListProductRepository? = null
        fun getInstance(pref: UserPreference): ListProductRepository =
            instance ?: synchronized(this) {
                instance ?: ListProductRepository(pref)
            }.also { instance = it }
    }

}