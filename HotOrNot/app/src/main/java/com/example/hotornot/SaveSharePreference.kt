package com.example.hotornot

import android.content.Context
import com.example.hotornot.Constants.Companion.SHARED_PREFERENCE_KEY

class SaveSharePreference(context: Context) {

    private val userSharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)

    fun putBoolean(key: String, value: Boolean) {
        userSharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return userSharedPreferences.getBoolean(key, false)
    }

    fun setUser(key: String, value: String) {
        userSharedPreferences.edit().putString(key, value).apply()
    }

    private fun getUser(key: String): String? {
        return userSharedPreferences.getString(key, null)
    }

    fun putInt(key: String, value: Int) {
        userSharedPreferences.edit().putInt(key, value).apply()
    }

    fun getInt(key: String): Int {
        return userSharedPreferences.getInt(key, 0)
    }

    fun saveUserDates(firstNameEditText: String, lastNameEditText: String, emailEditText: String) {
        val editor = userSharedPreferences.edit()
        editor.apply {
            setUser("firstName", firstNameEditText)
            putString("lastName", lastNameEditText)
            putString("email", emailEditText)
        }.apply()
    }

    fun getUserData() {
        val editor = userSharedPreferences.edit()
        editor.apply {
            getUser("firstName")
            getUser("lastName")
            getUser("email")
        }.apply()
    }
}

//    private val sharedPref =
//        context.getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)
//
//    fun putBoolean(key: String, value: Boolean){
//        sharedPref.edit().putBoolean(key, value).apply()
//    }
//
//    fun getBoolean(key: String): Boolean {
//        return sharedPref.getBoolean(key, false)
//    }
//
//    fun segUser(key: String, value: String){
//        sharedPref.edit().putString(key, value).apply()
//    }
//
//    fun getUser(key: String): String?{
//        return sharedPref.getString(key, null)
//    }
//
//    fun saveUserDates(firstNameEditText: String, lastNameEditText: String, emailEditText: String) {
//        val editor = sharedPref.edit()
//        editor.apply {
//            putString("firstName", firstNameEditText)
//            putString("lastName", lastNameEditText)
//            putString("email", emailEditText)
//        }.apply()
//    }
//
//    fun getUserData(){
//        val editor = sharedPref.edit()
//        editor.apply {
//
//        }.apply()
//    }


//    fun setUser(user: User) {
//        val json = .encodeToString(user)
//        sharedPref.edit().putString(PREF_SERIALIZABLE_USER, json).apply()
//    }

//    fun clearPreferenceUser() {
//        sharedPref.edit().clear().apply()
//    }
//    companion object{
//        private lateinit var sharedPref: SharedPreferences
//    }
//}
