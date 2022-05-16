package com.example.hotornot

import com.example.hotornot.enums.Gender
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val gender: Gender,
)
