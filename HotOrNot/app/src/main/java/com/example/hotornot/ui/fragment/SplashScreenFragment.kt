package com.example.hotornot.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hotornot.R
import com.example.hotornot.data.local.PreferencesUtil
import com.example.hotornot.data.repository.FriendRepository
import com.example.hotornot.databinding.FragmentSplashScreenBinding

private const val DELAY_TIME_IN_MILLIS = 2000L

class SplashScreen : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding
    private lateinit var preferencesUtil: PreferencesUtil
    private lateinit var friendRepository: FriendRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencesUtil = PreferencesUtil.getInstance(view.context)
        friendRepository = FriendRepository.getInstance(view.context)
        checkForExistedFriends()
        goToNextScreenWithDelay()
    }

    private fun goToNextScreenWithDelay() =
        Handler(Looper.getMainLooper()).postDelayed({ checkForSaveUser() }, DELAY_TIME_IN_MILLIS)

    private fun checkForSaveUser() {
        val user = preferencesUtil.getUser()
        if (user == null) goToRegistrationScreen()
        else goToMainScreen()
    }

    private fun checkForExistedFriends() {
        val friends = preferencesUtil.getFriends()
        if (friends.isEmpty()) friendRepository.saveFriends()
    }

    private fun goToRegistrationScreen() =
        findNavController().navigate(R.id.action_splashScreenFragment_to_registrationFormFragment)

    private fun goToMainScreen() =
        findNavController().navigate(R.id.action_splashScreenFragment_to_mainScreenFragment)
}