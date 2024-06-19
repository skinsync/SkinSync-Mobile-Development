package com.example.skinsync.activity.users.scan.result

import com.example.skinsync.data.UserModel
import com.example.skinsync.data.UserPreference
import kotlinx.coroutines.flow.Flow

class ResultRepository(private val pref: UserPreference
) {
    fun getSession(): Flow<UserModel> {
        return pref.getSession()
    }

    suspend fun setSaveSkinType(skinType:String){
        pref.setSaveSkinType(skinType)
    }

    companion object {
        @Volatile
        private var instance: ResultRepository? = null
        fun getInstance(pref: UserPreference): ResultRepository =
            instance ?: synchronized(this) {
                instance ?: ResultRepository(pref)
            }.also { instance = it }
    }
}