package com.example.hotornot

import kotlinx.serialization.Serializable

@Serializable
data class Friend(
    val name: String,
    val image: Int,
    val characteristics: List<String>,
    val isHot: Boolean
)