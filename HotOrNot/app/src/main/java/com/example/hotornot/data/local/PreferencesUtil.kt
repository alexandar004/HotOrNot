package com.example.hotornot.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.hotornot.data.model.Friend
import com.example.hotornot.data.model.User
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


private const val PREFERENCE_FILE_NAME = "com.example.hotornot"
private const val SERIALIZABLE_USER = "com.example.hotornot.data.local.PREF_USER"
private const val SERIALIZABLE_FRIEND = "com.example.hotornot.data.local.PREF_FRIEND"

class PreferencesUtil(context: Context) {

    init {
        sPref = context.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun getUser(): User? {
        val json: String = sPref.getString(SERIALIZABLE_USER, null) ?: return null
        return Json.decodeFromString(json)
    }

    fun setUser(user: User) {
        val json = Json.encodeToString(user)
        sPref.edit().putString(SERIALIZABLE_USER, json).apply()
    }

    fun clearPreferenceUser() =
        sPref.edit().clear().apply()

    fun getFriends(): List<Friend> {
        val json = sPref.getString(SERIALIZABLE_FRIEND, null) ?: return listOf()
        return Json.decodeFromString(json)
    }

    fun saveFriends(friends: List<Friend>) {
        val json = Json.encodeToString(friends)
        sPref.edit().putString(SERIALIZABLE_FRIEND, json).apply()
    }

    companion object {
        private lateinit var sPref: SharedPreferences
        private var instance: PreferencesUtil? = null

        fun getInstance(context: Context): PreferencesUtil {
            if (instance == null) {
                instance = PreferencesUtil(context)
            }
            return instance as PreferencesUtil
        }
    }
}