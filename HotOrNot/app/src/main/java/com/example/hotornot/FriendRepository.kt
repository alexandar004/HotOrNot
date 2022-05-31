package com.example.hotornot

import android.content.Context

class FriendRepository(val context: Context) {

    private lateinit var friends: List<Friend>

    fun setFriends() {
        friends = listOf(
            Friend(
                name = "Georgi",
                image = R.drawable.georgi,
                characteristics = getRandomCharacteristics(),
                email = "georgi@abv.bg",
                isHot = true),
            Friend(
                name = "Stan",
                image = R.drawable.stan,
                characteristics = getRandomCharacteristics(),
                email = "stan@abv.bg",
                isHot = false),
            Friend(
                name = "Nikola",
                image = R.drawable.nikola,
                characteristics = getRandomCharacteristics(),
                email = "nikola@abv.bg",
                isHot = null))
    }

    private fun getRandomCharacteristics(): List<String> {
        val randomCharacteristics = context.resources.getStringArray(R.array.characteristics_array)
        val randomCountOfCharacteristics: Int = (1..randomCharacteristics.size).random()

        return randomCharacteristics.toList().take(randomCountOfCharacteristics)
    }

    fun getFriends(): List<Friend> {
        setFriends()
        return friends
    }
}