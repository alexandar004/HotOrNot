package com.example.hotornot

import android.content.Context
import android.content.SharedPreferences
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PreferencesUtil(context: Context) {

    init {
        sPref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun getUser(): User? {
        val json: String = sPref.getString(PREF_SERIALIZABLE_USER, null) ?: return null
        return Json.decodeFromString(json)
    }

    fun setUser(user: User) {
        val json = Json.encodeToString(user)
        sPref.edit().putString(PREF_SERIALIZABLE_USER, json).apply()
    }

    fun clearPreferenceUser() =
        sPref.edit().clear().apply()

    companion object {

        private lateinit var sPref: SharedPreferences
        private const val PREF_FILE_NAME = "com.volasoftware.hotornot.data.local"
        private const val PREF_SERIALIZABLE_USER = "com.volasoftware.hotornot.data.local.PREF_USER"

        private var instance: PreferencesUtil? = null

        fun getInstance(context: Context): PreferencesUtil {
            if (instance == null) {
                instance = PreferencesUtil(context)
            }
            return instance as PreferencesUtil
        }
    }
}