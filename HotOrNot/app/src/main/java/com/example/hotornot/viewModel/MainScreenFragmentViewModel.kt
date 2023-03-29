package com.example.hotornot.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.example.hotornot.data.model.Friend
import com.example.hotornot.data.model.UiModel
import com.example.hotornot.data.model.User
import com.example.hotornot.data.repository.FriendRepository
import com.example.hotornot.data.repository.UserRepository
import com.example.hotornot.ui.fragment.MainScreenFragmentDirections

class MainScreenFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var userRepository: UserRepository
    private lateinit var friendRepository: FriendRepository

    private val _navigateLiveData = MutableLiveData<NavDirections>()
    val navigateLiveData: LiveData<NavDirections> = _navigateLiveData

    private var randomFriend: Friend? = null
    private var friendArgsValue: UiModel? = null

    private val _fetchUiModel = MutableLiveData(UiModel(
        isButtonHotVisible = true, isButtonNotVisible = true, isRatedFriend = false, randomFriend))
    val fetchUiModel: LiveData<UiModel> = _fetchUiModel

    init {
        initData()
        onViewResumed()
    }

    private fun initData() {
        userRepository = UserRepository.getInstance(getApplication())
        friendRepository = FriendRepository.getInstance(getApplication())
    }

    fun getUser(): User? = userRepository.getUser()

    fun clearPref() = userRepository.clearPreferenceUser()

    fun ratedFriend(isHot: Boolean) {
        val friendId = fetchUiModel.value?.friend?.friendId ?: return
        friendRepository.updateFriend(isHot, friendId)

        if (isHot) {
            friendRepository.likedFriends.add(randomFriend)
        }

        Log.d("AAA", "${friendRepository.likedFriends}")

        onViewResumed()
    }

    fun navigateToProfileScreen() {
        val navDirection =
            MainScreenFragmentDirections.actionMainScreenFragmentToProfileScreenFragment()
        _navigateLiveData.postValue(navDirection)
    }

    fun onViewResumed() {
        randomFriend = friendRepository.getRandomFriend()
        friendArgsValue = _fetchUiModel.value
        loadRandomFriends()
        friendArgsValue?.friend = randomFriend
        _fetchUiModel.value = friendArgsValue
    }

    private fun loadRandomFriends() {
        randomFriend?.let {
            friendArgsValue?.isButtonNotVisible = !it.isGeorgi()
            friendArgsValue?.isButtonHotVisible = !it.isStan()
        }
        friendArgsValue?.isRatedFriend = (randomFriend != null)

        if (randomFriend == null) {
            friendArgsValue?.isButtonHotVisible = false
            friendArgsValue?.isButtonNotVisible = false
        }
    }
}