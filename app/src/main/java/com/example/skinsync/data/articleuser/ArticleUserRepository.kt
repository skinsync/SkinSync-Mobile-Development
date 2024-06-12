package com.example.skinsync.data.articleuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.skinsync.data.setup.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleUserRepository(private val apiService: ApiService) {
    suspend fun getUserArticles(page: Int, limit: Int, sortBy: String, order: String, search: String): MutableLiveData<ArticleUserResponse?> {
        val data = MutableLiveData<ArticleUserResponse?>()

        apiService.getUserArticles(page, limit, sortBy, order, search).enqueue(object :
            Callback<ArticleUserResponse> {
            override fun onResponse(call: Call<ArticleUserResponse>, response: Response<ArticleUserResponse>) {
                if (response.isSuccessful) {
                    data.value = response.body()
                } else {
                    data.value = null // Handle error case
                }
            }

            override fun onFailure(call: Call<ArticleUserResponse>, t: Throwable) {
                data.value = null // Handle failure
            }
        })

        return data
    }

}