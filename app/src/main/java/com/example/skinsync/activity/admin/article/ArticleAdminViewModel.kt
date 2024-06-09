package com.example.skinsync.activity.admin.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.skinsync.data.articleadmin.ArticleAdminRepository
import com.example.skinsync.data.articleadmin.ArticleData
import kotlinx.coroutines.launch

class ArticleAdminViewModel(private val repository: ArticleAdminRepository) : ViewModel() {

    //var stories: Flow<PagingData<ListStoryItem>>? = null

    var article: LiveData<PagingData<ArticleData>> = repository.getArticle().cachedIn(viewModelScope)
//
//    private val _storiesWithLocation = MutableLiveData<List<ArticleData>>()
//    val storiesWithLocation: LiveData<List<ArticleData>> = _storiesWithLocation

    var _isLoading = MutableLiveData<Boolean>()
    var isLoading: LiveData<Boolean> = _isLoading

//    init {
//        _isLoading.value = false
//    }
}