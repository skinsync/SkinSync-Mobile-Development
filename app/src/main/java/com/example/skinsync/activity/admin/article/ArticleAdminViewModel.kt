package com.example.skinsync.activity.admin.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skinsync.data.articleadmin.ArticleAdminRepository
import com.example.skinsync.data.articleadmin.ArticleData
import com.example.skinsync.data.articleadmin.ArticlesResponse
import kotlinx.coroutines.launch

class ArticleAdminViewModel(private val repository: ArticleAdminRepository) : ViewModel() {

    //var stories: Flow<PagingData<ListStoryItem>>? = null

    var article: LiveData<PagingData<ArticleData>> = repository.getArticle().cachedIn(viewModelScope)

    private val _addArticleResponse = MutableLiveData<ArticlesResponse?>()
    val addArticleResponse: LiveData<ArticlesResponse?> get() = _addArticleResponse
//
//    private val _storiesWithLocation = MutableLiveData<List<ArticleData>>()
//    val storiesWithLocation: LiveData<List<ArticleData>> = _storiesWithLocation

    var _isLoading = MutableLiveData<Boolean>()
    var isLoading: LiveData<Boolean> = _isLoading

//    init {
//        _isLoading.value = false
//    }
    fun addArticle(title: String, deskripsi: String, picture: String, url: String) {
        viewModelScope.launch {
            val response = repository.addArticle(title, deskripsi, picture, url)
            _addArticleResponse.value = response
        }
    }
}