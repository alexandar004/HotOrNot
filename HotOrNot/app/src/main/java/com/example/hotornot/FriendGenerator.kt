package com.example.hotornot

class FriendGenerator {

    private val stanImg = R.drawable.first_people
    private val georgiImg = R.drawable.second_people
    private val nikolaImg = R.drawable.third_people

    private val georgi = Friend("Georgi", georgiImg)
    private val stan = Friend("Stan", stanImg)
    private val nikola = Friend("Nikola", nikolaImg)

    private val friendList = listOf(nikola, stan, georgi)

    val randomElement = friendList.asSequence().shuffled().take(friendList.size).toList()
}