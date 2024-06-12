package com.example.skinsync.data.setup

import android.content.Context
import com.example.skinsync.data.auth.AuthRepository
import com.example.skinsync.data.UserPreference
import com.example.skinsync.data.articleadmin.ArticleAdminRepository
import com.example.skinsync.data.articleuser.ArticleUserRepository
import com.example.skinsync.data.dataStore
import com.example.skinsync.viewmodel.CombinedRepository

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return AuthRepository.getInstance(pref)
    }
    fun provideArticleAdminRepository(context: Context): ArticleAdminRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return ArticleAdminRepository.getInstance(pref)
    }

    fun provideArticleUserRepository(apiService: ApiService): ArticleUserRepository {
        return ArticleUserRepository(apiService)
    }

    fun provideCombinedRepository(context: Context): CombinedRepository {
        val apiService = ApiConfig.getApiService() // Mengambil ApiService dari ApiConfig
        return CombinedRepository(
            provideAuthRepository(context),
            provideArticleAdminRepository(context),
            provideArticleUserRepository(apiService) // Ubah pemanggilan ini sesuai dengan fungsi yang sudah diperbaiki di atas
        )
    }
}