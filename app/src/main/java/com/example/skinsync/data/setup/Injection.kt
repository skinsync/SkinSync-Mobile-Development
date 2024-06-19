package com.example.skinsync.data.setup

import android.content.Context
import com.example.skinsync.data.auth.AuthRepository
import com.example.skinsync.data.UserPreference
import com.example.skinsync.data.articleadmin.ArticleAdminRepository
import com.example.skinsync.activity.users.article.ArticleUserRepository
import com.example.skinsync.activity.users.listproduct.ListProductRepository
import com.example.skinsync.activity.users.profile.ProfileRepository
import com.example.skinsync.activity.users.scan.result.ResultRepository
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

    fun provideArticleUserRepository(context: Context): ArticleUserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return ArticleUserRepository.getInstance(pref)
    }

    fun provideProfileRepository(context: Context): ProfileRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return ProfileRepository.getInstance(pref)
    }

    fun provideListProductRepository(context: Context): ListProductRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return ListProductRepository.getInstance(pref)
    }

    fun provideResultRepository(context: Context): ResultRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return ResultRepository.getInstance(pref)
    }


    fun provideCombinedRepository(context: Context): CombinedRepository {
        return CombinedRepository(
            provideAuthRepository(context),
            provideArticleAdminRepository(context),
            provideArticleUserRepository(context),
            provideProfileRepository(context),
            provideListProductRepository(context),
            provideResultRepository(context)
        )
    }
}