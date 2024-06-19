package com.example.skinsync.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skinsync.activity.users.article.ArticleUserRepository
import com.example.skinsync.activity.users.listproduct.ListProductRepository
import com.example.skinsync.activity.users.listproduct.ProductDataItem
import com.example.skinsync.data.UserModel
import com.example.skinsync.data.articleadmin.ArticleData
import kotlinx.coroutines.launch

class ListProductViewModel (private val repository: ListProductRepository) : ViewModel() {

    var products: LiveData<PagingData<ProductDataItem>> = repository.getProducts().cachedIn(viewModelScope)
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