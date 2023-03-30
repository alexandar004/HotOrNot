package com.example.hotornot.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.example.hotornot.data.repository.FriendRepository
import com.example.hotornot.data.repository.UserRepository
import com.example.hotornot.ui.fragment.SplashScreenDirections

class SplashScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val _navigationLiveData = MutableLiveData<NavDirections>()
    var navigationLiveData: LiveData<NavDirections> = _navigationLiveData
    private lateinit var friendRepository: FriendRepository
    private lateinit var userRepository: UserRepository

    init {
        initializedData()
    }

    private fun initializedData() {
        friendRepository = FriendRepository(getApplication())
        userRepository = UserRepository(getApplication())
    }

    private fun onLoadUser() {
        val user = userRepository.getUser()
        if (user != null) {
            navigateToMainScreen()
        } else {
            navigateToNetworkErrorScreen()
        }
    }

    private fun navigateToMainScreen() {
        val navDirection =
            SplashScreenDirections.actionSplashScreenFragmentToMainScreenFragment()
        _navigationLiveData.postValue(navDirection)
    }

    private fun navigateToNetworkErrorScreen() {
        val navDirections =
            SplashScreenDirections.actionSplashScreenFragmentToNoNetworkConnectionScreen()
        _navigationLiveData.postValue(navDirections)
    }

    private fun onLoadFriends() = friendRepository.generateFriends()

    fun onLoadData() {
        onLoadUser()
        onLoadFriends()
    }
}