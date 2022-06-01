package com.example.hotornot

import android.content.Context

private const val MIN_NUMBER_CHARACTERISTICS = 1

class FriendRepository(val context: Context) {

    private lateinit var friends: List<Friend>

    fun generateFriends() {
        friends = listOf(
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
                isHot = null)
        )
    }

    private fun getRandomCharacteristics(): List<String> {
        val randomCharacteristics = context.resources.getStringArray(R.array.characteristics_array)
        val randomCountOfCharacteristics: Int =
            (MIN_NUMBER_CHARACTERISTICS..randomCharacteristics.size).random()
        return randomCharacteristics.toList().take(randomCountOfCharacteristics)
    }

    fun getFriends(): List<Friend> {
        generateFriends()
        return friends
    }
}