package com.example.hotornot.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.hotornot.data.repository.FriendRepository

class LocationScreenFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var friendRepository: FriendRepository

    init {
        initializedLiveData()
    }

    private fun initializedLiveData() {
        friendRepository = FriendRepository.getInstance(getApplication())
    }

    fun clearRatedFriends() = friendRepository.getAllSavedFriends()
}