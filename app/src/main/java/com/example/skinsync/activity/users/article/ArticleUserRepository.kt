package com.example.skinsync.activity.users.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.skinsync.data.UserModel
import com.example.skinsync.data.UserPreference
import com.example.skinsync.data.articleadmin.ArticleData
import com.example.skinsync.data.articleadmin.ArticlePagingSource
import com.example.skinsync.data.articleadmin.ArticleRequest
import com.example.skinsync.data.articleadmin.ArticlesResponse
import com.example.skinsync.data.setup.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class ArticleUserRepository(private val pref: UserPreference) {

    fun getSession(): Flow<UserModel> {
        return pref.getSession()
    }

    fun getArticles(): LiveData<PagingData<ArticleData>> = liveData {
        val token = pref.getSession().first().token
        val apiService = ApiConfig.getApiService(token)
        emitSource(
            Pager(
                config = PagingConfig(pageSize = 5, enablePlaceholders = false),
                pagingSourceFactory = { ArticlePagingSource(apiService, "") }
            ).liveData
        )
    }

    fun searchArticles(): LiveData<PagingData<ArticleData>> = liveData {
        val token = pref.getSession().first().token
        val query = pref.getSession().first().skinType
        val apiService = ApiConfig.getApiService(token)
        emitSource(
            Pager(
                config = PagingConfig(pageSize = 5, enablePlaceholders = false),
                pagingSourceFactory = { ArticlePagingSource(apiService, query) }
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
        private var instance: ArticleUserRepository? = null
        fun getInstance(pref: UserPreference): ArticleUserRepository =
            instance ?: synchronized(this) {
                instance ?: ArticleUserRepository(pref)
            }.also { instance = it }
    }
}