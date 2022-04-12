package com.example.hotornot

class FriendGenerator {

    private val stanImg = R.drawable.georgi
    private val georgiImg = R.drawable.nikola
    private val nikolaImg = R.drawable.stan

    var friendList = listOf(
        Friend("Stan", stanImg),
        Friend("Georgi", georgiImg),
        Friend("Nikola", nikolaImg)
    )
}