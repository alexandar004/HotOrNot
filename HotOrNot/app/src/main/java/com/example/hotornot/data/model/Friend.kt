package com.example.hotornot.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Friend(
    val name: String,
    val image: Int,
    val characteristics: List<String>,
    val email: String,
    val isHot: Boolean?
)