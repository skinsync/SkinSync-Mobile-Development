package com.example.skinsync.viewmodel

import android.content.Context
import com.example.skinsync.dataclass.AuthRepository
import com.example.skinsync.dataclass.UserPreference
import com.example.skinsync.dataclass.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return AuthRepository.getInstance(pref)
    }
}