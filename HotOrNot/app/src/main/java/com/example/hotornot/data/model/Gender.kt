package com.example.hotornot.data.model

import com.example.hotornot.R

enum class Gender(val stringResourceId: Int, val iconResource: Int) {
    MALE(R.string.gender_male, R.drawable.ic_man),
    FEMALE(R.string.gender_female, R.drawable.ic_girl),
    OTHER(R.string.gender_other, R.drawable.ic_other_gender)
}