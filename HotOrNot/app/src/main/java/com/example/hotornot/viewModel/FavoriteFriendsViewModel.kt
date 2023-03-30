package com.example.hotornot.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.hotornot.data.model.User
import com.example.hotornot.data.repository.UserRepository

class FavoriteFriendsViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var userRepository: UserRepository


    init {
        initData()
    }

    private fun initData() {
        userRepository = UserRepository.getInstance(getApplication())
//        friendRepository = FriendRepository.getInstance(getApplication())
    }

    fun getUser(): User? = userRepository.getUser()

}