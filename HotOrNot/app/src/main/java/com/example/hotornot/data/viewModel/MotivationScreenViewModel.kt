package com.example.hotornot.data.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.hotornot.ui.fragment.MotivationScreenFragmentDirections

class MotivationScreenViewModel : ViewModel() {

    private val _navigationLiveData = MutableLiveData<NavDirections>()
    val navigateLiveData: LiveData<NavDirections> = _navigationLiveData

    fun onMotivationButtonClicked() {
        val navDirection =
            MotivationScreenFragmentDirections.actionMotivationScreenToMainScreenFragment()
        _navigationLiveData.postValue(navDirection)
    }
}