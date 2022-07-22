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

    private lateinit var userRepository: UserRepository
    private lateinit var friendRepository: FriendRepository

    private val _navigateLiveData = MutableLiveData<NavDirections>()
    val navigateLiveData: LiveData<NavDirections> = _navigateLiveData

//    private val _randomisedFriendLiveData = MutableLiveData<Friend?>()
//    val randomisedFriendLiveData: LiveData<Friend?> = _randomisedFriendLiveData
//
//    private val _hasFriendToRateLiveData = MutableLiveData<Boolean>()
//    val hasFriendToRateLiveData: LiveData<Boolean> = _hasFriendToRateLiveData

    private val _isButtonHotVisible = MutableLiveData<Boolean>()
    val isButtonHotVisible: LiveData<Boolean> = _isButtonHotVisible

    private val _isButtonNotVisible = MutableLiveData<Boolean>()
    val isButtonNotVisible: LiveData<Boolean> = _isButtonNotVisible

    private var friend: Friend? = null

    init {
        initData()
        loadRandomFriend()
        navigateToProfileScreen()
    }

    private fun initData() {
        userRepository = UserRepository.getInstance(getApplication())
        friendRepository = FriendRepository.getInstance(getApplication())
    }

    fun getUser(): User? = userRepository.getUser()

    fun clearPref() = userRepository.clearPreferenceUser()

    fun ratedFriend(isHot: Boolean) {
        val friendId = randomisedFriendLiveData.value?.friendId ?: return
        friendRepository.updateFriend(isHot, friendId)
        loadRandomFriend()
    }

    private fun navigateToProfileScreen() {
        val navDirection =
            MainScreenFragmentDirections.actionMainScreenFragmentToProfileScreenFragment()
        _navigateLiveData.postValue(navDirection)
    }

    private fun loadRandomFriend() {
        friend = friendRepository.getRandomFriend()
        friend?.let {
            _isButtonHotVisible.postValue(!it.isStan())
            _isButtonNotVisible.postValue(!it.isGeorgi())
        }
        _hasFriendToRateLiveData.postValue(friend != null)
        if (friend == null) {
            _isButtonHotVisible.postValue(false)
            _isButtonNotVisible.postValue(false)
        }
        _randomisedFriendLiveData.postValue(friend)
    }
}