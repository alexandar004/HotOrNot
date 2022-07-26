package com.example.hotornot.viewModel

import android.app.Application
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

    private val _randomisedFriendLiveData = MutableLiveData<Friend?>()
    val randomisedFriendLiveData: LiveData<Friend?> = _randomisedFriendLiveData

    private val _fetchUiModel = MutableLiveData(UiModel(
        isButtonHotVisible = true, isButtonNotVisible = true, isRatedFriend = false))
    val fetchUiModel: LiveData<UiModel> = _fetchUiModel

    private var friend: Friend? = null
    private var friendArgsValue: UiModel? = null

    init {
        initData()
        onViewResumed()
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
        onViewResumed()
    }

    private fun navigateToProfileScreen() {
        val navDirection =
            MainScreenFragmentDirections.actionMainScreenFragmentToProfileScreenFragment()
        _navigateLiveData.postValue(navDirection)
    }


    fun onViewResumed() {
        friend = friendRepository.getRandomFriend()
        friendArgsValue = _fetchUiModel.value
        _randomisedFriendLiveData.value = friend
        _fetchUiModel.value = friendArgsValue
        loadRandomFriends()
    }

    private fun loadRandomFriends() {
        friend?.let {
            friendArgsValue?.isButtonNotVisible = !it.isGeorgi()
            friendArgsValue?.isButtonHotVisible = !it.isStan()
        }
        friendArgsValue?.isRatedFriend = (friend != null)

        if (friend == null) {
            friendArgsValue?.isButtonHotVisible = false
            friendArgsValue?.isButtonNotVisible = false
        }
    }
}