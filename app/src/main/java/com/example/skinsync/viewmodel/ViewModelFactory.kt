package com.example.skinsync.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.skinsync.activity.admin.article.ArticleAdminViewModel
import com.example.skinsync.data.articleadmin.ArticleAdminRepository
import com.example.skinsync.activity.users.article.ArticleUserRepository
import com.example.skinsync.activity.users.listproduct.ListProductRepository
import com.example.skinsync.activity.users.profile.ProfileRepository
import com.example.skinsync.activity.users.profile.ProfileViewModel
import com.example.skinsync.data.auth.AuthRepository
import com.example.skinsync.data.setup.Injection

class CombinedRepository(
    val authRepository: AuthRepository,
    val articleAdminRepository: ArticleAdminRepository,
    val articleUserRepository: ArticleUserRepository,
    val profileRepository: ProfileRepository,
    val listProductRepository: ListProductRepository
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
            modelClass.isAssignableFrom(ArticleAdminViewModel::class.java) -> {
                ArticleAdminViewModel(combinedRepository.articleAdminRepository) as T
            }
            modelClass.isAssignableFrom(ArticleUserViewModel::class.java) -> {
                ArticleUserViewModel(combinedRepository.articleUserRepository) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(combinedRepository.profileRepository) as T
            }
            modelClass.isAssignableFrom(ListProductViewModel::class.java) -> {
                ListProductViewModel(combinedRepository.listProductRepository) as T
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