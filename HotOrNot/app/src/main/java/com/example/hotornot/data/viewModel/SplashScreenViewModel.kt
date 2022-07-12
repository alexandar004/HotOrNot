package com.example.hotornot.data.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hotornot.data.model.User
import com.example.hotornot.data.repository.FriendRepository
import com.example.hotornot.data.repository.UserRepository

class SplashScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val _userData = MutableLiveData<User>()
    var userData: LiveData<User> = _userData
    private lateinit var friendRepository: FriendRepository
    private lateinit var userRepository: UserRepository

    init {
        initializedData()
    }

    private fun initializedData() {
        friendRepository = FriendRepository(getApplication())
        userRepository = UserRepository(getApplication())
    }

    fun loadFriends() {
        friendRepository.generateFriends()
    }

    fun getUser() {
        val user = userRepository.getUser()
        _userData.postValue(user!!)
    }

}