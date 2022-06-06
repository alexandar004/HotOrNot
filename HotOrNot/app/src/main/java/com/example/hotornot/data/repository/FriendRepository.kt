package com.example.hotornot.data.repository

import android.content.Context
import com.example.hotornot.R
import com.example.hotornot.data.local.PreferencesUtil
import com.example.hotornot.data.model.Friend


private const val MIN_NUMBER_CHARACTERISTICS = 1

class FriendRepository(val context: Context) {

    private val preferencesUtil: PreferencesUtil = PreferencesUtil.getInstance(context)

    private fun generateFriends(): List<Friend> {
        return listOf(
            Friend(
                name = context.getString(R.string.georgi),
                image = R.drawable.georgi,
                characteristics = getRandomCharacteristics(),
                email = context.getString(R.string.georgi_email),
                isHot = true),
            Friend(
                name = context.getString(R.string.stan),
                image = R.drawable.stan,
                characteristics = getRandomCharacteristics(),
                email = context.getString(R.string.stan_email),
                isHot = false),
            Friend(
                name = context.getString(R.string.nikola),
                image = R.drawable.nikola,
                characteristics = getRandomCharacteristics(),
                email = context.getString(R.string.nikola_email),
                isHot = null),
            Friend(
                name = context.getString(R.string.petar),
                image = R.drawable.pesho,
                characteristics = getRandomCharacteristics(),
                email = context.getString(R.string.petar_email),
                isHot = null),
            Friend(
                name = context.getString(R.string.stefan),
                image = R.drawable.stefan,
                characteristics = getRandomCharacteristics(),
                email = context.getString(R.string.stefan),
                isHot = null)
        )
    }

    fun saveFriends() {
        preferencesUtil.saveFriends(generateFriends())
    }

    private fun getRandomCharacteristics(): List<String> {
        val randomCharacteristics = context.resources.getStringArray(R.array.characteristics_array)
        val randomCountOfCharacteristics: Int =
            (MIN_NUMBER_CHARACTERISTICS..randomCharacteristics.size).random()
        return randomCharacteristics.toList().take(randomCountOfCharacteristics)
    }

    fun getAllSavedFriends(): List<Friend> {
        return generateFriends()
    }

    companion object {

        private var instance: FriendRepository? = null

        fun getInstance(context: Context): FriendRepository {
            if (instance == null) {
                instance = FriendRepository(context)
            }
            return instance as FriendRepository
        }
    }
}