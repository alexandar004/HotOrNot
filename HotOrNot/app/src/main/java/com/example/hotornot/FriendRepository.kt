package com.example.hotornot

import android.content.Context

class FriendRepository(val context: Context) {

    private lateinit var friends: List<Friend>

    fun setFriends() {
        friends = listOf(
            Friend("Georgi", R.drawable.georgi, getRandomCharacteristics(), true),
            Friend("Stan", R.drawable.stan, getRandomCharacteristics(), false),
            Friend("Nikola", R.drawable.nikola, getRandomCharacteristics(), true))
    }

    private fun getRandomCharacteristics(): List<String> {
        val randomCharacteristics = context.resources.getStringArray(R.array.characteristics_array)
        val randomNumberOfCharacteristic: Int =
            (1..randomCharacteristics.size).random()
        return randomCharacteristics.toList().shuffled().take(randomNumberOfCharacteristic)
    }

    fun getFriends(): List<Friend> {
        setFriends()
        return friends
    }
}