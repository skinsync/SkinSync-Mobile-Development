package com.example.skinsync.activity.users.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.skinsync.data.UserPreference
import com.example.skinsync.data.articleadmin.ArticleData
import com.example.skinsync.data.articleadmin.ArticlePagingSource
import com.example.skinsync.data.setup.ApiConfig
import kotlinx.coroutines.flow.first

class ArticleUserRepository(private val pref: UserPreference
) {
    fun getArticle(): LiveData<PagingData<ArticleData>> = liveData {
        val token = pref.getSession().first().token
        val apiService = ApiConfig.getApiService(token)
        emitSource(
            Pager(
                config = PagingConfig(pageSize = 5, enablePlaceholders = false),
                pagingSourceFactory = { ArticlePagingSource(apiService) }
            ).liveData
        )
    }
//    suspend fun getUserArticles(page: Int, limit: Int, sortBy: String, order: String, search: String): MutableLiveData<ArticleUserResponse?> {
//        val data = MutableLiveData<ArticleUserResponse?>()
//
//        apiService.getUserArticles(page, limit, sortBy, order, search).enqueue(object :
//            Callback<ArticleUserResponse> {
//            override fun onResponse(call: Call<ArticleUserResponse>, response: Response<ArticleUserResponse>) {
//                if (response.isSuccessful) {
//                    data.value = response.body()
//                } else {
//                    data.value = null // Handle error case
//                }
//            }
//
//            override fun onFailure(call: Call<ArticleUserResponse>, t: Throwable) {
//                data.value = null // Handle failure
//            }
//        })
//
//        return data
//    }
    companion object {
        @Volatile
        private var instance: ArticleUserRepository? = null
        fun getInstance(pref: UserPreference): ArticleUserRepository =
            instance ?: synchronized(this) {
                instance ?: ArticleUserRepository(pref)
            }.also { instance = it }
    }

}