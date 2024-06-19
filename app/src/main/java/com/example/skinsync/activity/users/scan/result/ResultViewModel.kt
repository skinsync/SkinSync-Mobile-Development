package com.example.skinsync.activity.users.scan.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.skinsync.activity.users.article.ArticleUserRepository
import com.example.skinsync.data.UserModel
import kotlinx.coroutines.launch

class ResultViewModel(private val repository: ResultRepository) : ViewModel() {
    var _isLoading = MutableLiveData<Boolean>()
    var isLoading: LiveData<Boolean> = _isLoading

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun setSaveSkinType(skinType:String){
        viewModelScope.launch {
            repository.setSaveSkinType(skinType)
        }
    }
}