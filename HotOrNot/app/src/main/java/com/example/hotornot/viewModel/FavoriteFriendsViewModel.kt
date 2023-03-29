package com.example.hotornot.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.hotornot.data.repository.FriendRepository
import com.example.hotornot.ui.adapter.FavoriteFriendsAdapter

class FavoriteFriendsViewModel(application: Application) : AndroidViewModel(application) {

    var friendRepository: FriendRepository = FriendRepository.getInstance(application)
    private var adapter = FavoriteFriendsAdapter()

    init {
        friendRepository.likedFriends = adapter.items
        Log.d("TTT", "${friendRepository.likedFriends}")
    }
}