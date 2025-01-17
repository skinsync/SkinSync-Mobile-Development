package com.example.skinsync.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skinsync.data.articleadmin.ArticleData
import com.example.skinsync.activity.users.article.ArticleUserRepository
import com.example.skinsync.data.UserModel

class ArticleUserViewModel(private val repository: ArticleUserRepository) : ViewModel() {

    var articles: LiveData<PagingData<ArticleData>> = repository.getArticles().cachedIn(viewModelScope)
    var searchArticles: LiveData<PagingData<ArticleData>> = repository.searchArticles().cachedIn(viewModelScope)
    //private val _articles = MutableLiveData<ArticleUserResponse?>()
    //val articles: LiveData<ArticleUserResponse?> get() = _articles
    var _isLoading = MutableLiveData<Boolean>()
    var isLoading: LiveData<Boolean> = _isLoading

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

//    fun fetchArticles(page: Int, limit: Int, sortBy: String, order: String, search: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            val response = repository.getUserArticles(page, limit, sortBy, order, search)
//            _articles.postValue(response.value)
//        }
//    }
}