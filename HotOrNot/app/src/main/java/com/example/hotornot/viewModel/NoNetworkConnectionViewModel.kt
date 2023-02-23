package com.example.hotornot.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hotornot.data.util.NetworkHelper
import com.example.hotornot.data.util.SingleLiveEvent

class NoNetworkConnectionViewModel(application: Application) : AndroidViewModel(application) {

    private val _onNetworkConnected = MutableLiveData<Boolean>()
    val onNetworkConnected: LiveData<Boolean> = _onNetworkConnected
    private val _swipeVisibility = SingleLiveEvent<Boolean>()
    val swipeTextVisibility: LiveData<Boolean> = _swipeVisibility

    fun checkNetworkConnection() {
        _swipeVisibility.value = true
        if (NetworkHelper.isNetworkConnected(getApplication())) {
            _onNetworkConnected.value = true
        } else {
            _onNetworkConnected.value = false
            _swipeVisibility.call()
        }
    }
}

