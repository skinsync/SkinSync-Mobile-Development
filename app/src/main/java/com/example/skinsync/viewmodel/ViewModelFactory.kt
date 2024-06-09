package com.example.skinsync.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skinsync.activity.admin.article.ArticleAdminViewModel
import com.example.skinsync.activity.admin.dashboard.DashboardViewModel
import com.example.skinsync.data.articleadmin.ArticleAdminRepository
import com.example.skinsync.data.auth.AuthRepository
import com.example.skinsync.data.setup.Injection

class CombinedRepository(
    val authRepository: AuthRepository,
    val articleAdminRepository: ArticleAdminRepository,
)
class ViewModelFactory(private val combinedRepository: CombinedRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(combinedRepository.authRepository) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(combinedRepository.authRepository) as T
            }
            modelClass.isAssignableFrom(DashboardViewModel::class.java) -> {
                DashboardViewModel(combinedRepository.authRepository) as T
            }
            modelClass.isAssignableFrom(ArticleAdminViewModel::class.java) -> {
                ArticleAdminViewModel(combinedRepository.articleAdminRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(
                    Injection.provideCombinedRepository(context)
                ).also { INSTANCE = it }
            }
        }
    }
}