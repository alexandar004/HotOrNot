package com.example.hotornot.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.example.hotornot.data.model.User
import com.example.hotornot.data.repository.UserRepository
import com.example.hotornot.ui.fragment.ProfileScreenFragmentDirections

class ProfileScreenFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var userRepository: UserRepository

    private val _navigationLiveData = MutableLiveData<NavDirections>()
    val navigationLiveData: LiveData<NavDirections> = _navigationLiveData
    private val _userLiveData = MutableLiveData<User?>()
    val userLiveData: LiveData<User?> = _userLiveData

    init {
        initializedData()
        getUser()
    }

    private fun initializedData() {
        userRepository = UserRepository(getApplication())
    }

    fun navigateBtnChangeLocation() {
        val navDirection =
            ProfileScreenFragmentDirections.actionProfileScreenFragmentToLocationScreen()
        _navigationLiveData.postValue(navDirection)
    }

    private fun getUser() {
        val user = userRepository.getUser()
        _userLiveData.value = user
    }
}