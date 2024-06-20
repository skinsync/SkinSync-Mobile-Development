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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class ArticleAdminRepository (private val pref: UserPreference
) {
    fun getArticle(): LiveData<PagingData<ArticleData>> = liveData {
        val token = pref.getSession().first().token
        val apiService = ApiConfig.getApiService(token)
        emitSource(
            Pager(
                config = PagingConfig(pageSize = 5, enablePlaceholders = false),
                pagingSourceFactory = { ArticlePagingSource(apiService, "") }
            ).liveData
        )
    }

    suspend fun addArticle(title: String, deskripsi: String, picture: String, url: String): ArticlesResponse? {
        val token = pref.getSession().first().token
        val apiService = ApiConfig.getApiService(token)
        return withContext(Dispatchers.IO) {
            try {
                val articleRequest = ArticleRequest(title, deskripsi, picture, url)
                val response = apiService.postArticle(articleRequest).awaitResponse()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    // Handle error
                    null
                }
            } catch (e: Exception) {
                // Handle exception (e.g., log error, return a specific error response)
                null
            }
        }
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