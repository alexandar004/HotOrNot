package com.example.hotornot.data.model

import kotlinx.serialization.Serializable
import java.util.*

private const val HOT_NAME = "Georgi"
private const val NOT_HOT_NAME = "Stan"

@Serializable
data class Friend(
    val name: String,
    val image: Int,
    val characteristics: List<String>,
    val email: String,
) {
    var isHot: Boolean? = null
    val friendId = UUID.randomUUID().toString()

    fun isGeorgi() = name == HOT_NAME

    fun isStan() = name == NOT_HOT_NAME
}