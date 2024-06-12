package com.example.skinsync.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.skinsync.data.articleuser.ArticleUserRepository
import com.example.skinsync.data.articleuser.ArticleUserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleUserViewModel(private val repository: ArticleUserRepository) : ViewModel() {
    private val _articles = MutableLiveData<ArticleUserResponse?>()
    val articles: LiveData<ArticleUserResponse?> get() = _articles

    fun fetchArticles(page: Int, limit: Int, sortBy: String, order: String, search: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getUserArticles(page, limit, sortBy, order, search)
            _articles.postValue(response.value)
        }
    }
}