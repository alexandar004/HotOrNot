package com.example.hotornot.data.repository

import android.content.Context
import com.example.hotornot.data.local.PreferencesUtil
import com.example.hotornot.data.model.User

class UserRepository(context: Context) {


    private val preferencesUtil: PreferencesUtil = PreferencesUtil.getInstance(context)

    fun getUser(): User? {
        return preferencesUtil.getUser()
    }

    fun setUser(user: User) {
        preferencesUtil.setUser(user)
    }

    fun clearPreferenceUser(){
        preferencesUtil.deleteUserFromSharedPreference()
    }

    companion object {

        private var instance: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            if (instance == null) {
                instance = UserRepository(context)
            }
            return instance as UserRepository
        }
    }
}