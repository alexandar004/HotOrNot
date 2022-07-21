package com.example.hotornot.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.example.hotornot.data.model.Friend
import com.example.hotornot.data.model.User
import com.example.hotornot.data.repository.FriendRepository
import com.example.hotornot.data.repository.UserRepository
import com.example.hotornot.ui.fragment.MainScreenFragmentDirections

class MainScreenFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var friendRepository: FriendRepository
    private lateinit var userRepository: UserRepository
    private lateinit var randomFriend: Friend
    private lateinit var allFriends: List<Friend>

    private val _navigateLiveData = MutableLiveData<NavDirections>()
    val navigateLiveData: LiveData<NavDirections> = _navigateLiveData
    private val _friendLiveData = MutableLiveData<Friend>()
    val friendLiveData: LiveData<Friend> = _friendLiveData
    private val _visibilityLiveData = MutableLiveData<Boolean>()
    val visibilityLiveData: LiveData<Boolean> = _visibilityLiveData

    init {
        initializedData()
        initViews()
        navigateToProfileScreen()
        getRandomFriend()
    }

    private fun initializedData() {
        friendRepository = FriendRepository(getApplication())
        userRepository = UserRepository(getApplication())
    }

    fun clearUserInfo() = userRepository.clearPreferenceUser()

    fun getUser(): User? = userRepository.getUser()

    private fun navigateToProfileScreen() {
        val navDirection =
            MainScreenFragmentDirections.actionMainScreenFragmentToProfileScreenFragment()
        _navigateLiveData.postValue(navDirection)
    }

    private fun getRandomFriend() {
        randomFriend = friendRepository.getAllSavedFriends().random()
        _friendLiveData.postValue(randomFriend)
//        checkForHotName(randomFriend)
    }

    private fun updateFriends(isHot: Boolean) {
        allFriends = friendRepository.getAllSavedFriends()
        allFriends.find { it.friendId == randomFriend.friendId }?.isHot = isHot
        friendRepository.saveFriends(allFriends)
    }

    fun initViews() {
        allFriends = friendRepository.getAllSavedFriends()
        val allRandomFriends = allFriends.filter { it.isHot == null }
        if (allRandomFriends.isNotEmpty()) {
            getRandomFriend()
        } else {
            _visibilityLiveData.postValue(true)
        }
    }

//    private fun checkForHotName(friend: Friend) {
//        if (friend.name == "Stan") {
//            _visibilityLiveData.postValue(true)
//        } else {
//            _visibilityLiveData.postValue(false)
//        }
//    }


    fun rateFriend(isHot: Boolean) {
        randomFriend.isHot = isHot
        updateFriends(isHot)
        initViews()
    }
}