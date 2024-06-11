package com.example.skinsync.data.articleadmin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.skinsync.data.UserPreference
import com.example.skinsync.data.setup.ApiConfig
import kotlinx.coroutines.flow.first

class ArticleAdminRepository (private val pref: UserPreference
) {
    fun getArticle(): LiveData<PagingData<ArticleData>> = liveData {
        val token = pref.getSession().first().token
        Log.i("Token: ", token)
        val apiService = ApiConfig.getApiService(token)
        emitSource(
            Pager(
                config = PagingConfig(pageSize = 5, enablePlaceholders = false),
                pagingSourceFactory = { ArticlePagingSource(apiService) }
            ).liveData
        )
    }

    companion object {
        @Volatile
        private var instance: ArticleAdminRepository? = null
        fun getInstance(pref: UserPreference): ArticleAdminRepository =
            instance ?: synchronized(this) {
                instance ?: ArticleAdminRepository(pref)
            }.also { instance = it }
    }
}