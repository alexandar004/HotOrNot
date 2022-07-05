package com.example.hotornot.data.repository

import android.content.Context
import com.example.hotornot.R
import com.example.hotornot.data.local.PreferencesUtil
import com.example.hotornot.data.model.Friend


private const val MIN_NUMBER_CHARACTERISTICS = 1

class FriendRepository(val context: Context) {

    private val preferencesUtil: PreferencesUtil = PreferencesUtil.getInstance(context)

    fun generateFriends() {
        val listOfFriends = listOf(
            Friend(
                name = context.getString(R.string.georgi),
                image = R.drawable.georgi,
                characteristics = getRandomCharacteristics(),
                email = context.getString(R.string.georgi_email),
            ),
            Friend(
                name = context.getString(R.string.stan),
                image = R.drawable.stan,
                characteristics = getRandomCharacteristics(),
                email = context.getString(R.string.stan_email),
            ),
            Friend(
                name = context.getString(R.string.nikola),
                image = R.drawable.nikola,
                characteristics = getRandomCharacteristics(),
                email = context.getString(R.string.nikola_email),
            ),
            Friend(
                name = context.getString(R.string.petar),
                image = R.drawable.pesho,
                characteristics = getRandomCharacteristics(),
                email = context.getString(R.string.petar_email),
            ),
            Friend(
                name = context.getString(R.string.stefan),
                image = R.drawable.stefan,
                characteristics = getRandomCharacteristics(),
                email = context.getString(R.string.stefan_email),
            )
        )
        saveFriends(listOfFriends)
    }

    fun getAllSavedFriends(): List<Friend> {
        return preferencesUtil.getFriends()
    }

    fun saveFriends(friends: List<Friend>) {
        preferencesUtil.saveFriends(friends)
    }

    fun deleteRatedFriends(){
        preferencesUtil.resetRatedFriends()
    }

    private fun getRandomCharacteristics(): List<String> {
        val randomCharacteristics = context.resources.getStringArray(R.array.characteristics_array)
        val randomCountOfCharacteristics: Int =
            (MIN_NUMBER_CHARACTERISTICS..randomCharacteristics.size).random()
        return randomCharacteristics.toList().take(randomCountOfCharacteristics)
    }

    companion object {

        private var instance: FriendRepository? = null

        fun getInstance(context: Context): FriendRepository {
            if (instance == null) {
                instance = FriendRepository(context.applicationContext)
            }
            return instance as FriendRepository
        }
    }
}