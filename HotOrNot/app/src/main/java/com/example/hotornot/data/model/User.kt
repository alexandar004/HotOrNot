package com.example.hotornot.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val gender: Gender,
    val interests: String,
)
