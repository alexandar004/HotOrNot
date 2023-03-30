package com.example.hotornot.data.model

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Friend(
    val name: String,
    val image: Int,
    val characteristics: List<String>,
    val email: String,
) {
    var isHot: Boolean? = null
    val friendId = UUID.randomUUID().toString()

}