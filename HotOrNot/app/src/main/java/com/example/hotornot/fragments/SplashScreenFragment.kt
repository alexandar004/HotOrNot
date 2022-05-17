package com.example.hotornot

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hotornot.databinding.FragmentSplashScreenBinding

private const val DELAY_TIME = 2000L

class SplashScreen : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding
    private lateinit var preferencesUtil: PreferencesUtil

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencesUtil = PreferencesUtil.getInstance(view.context)
        goToNextScreenWithDelay()
    }

    private fun goToNextScreenWithDelay() =
        Handler(Looper.getMainLooper())
            .postDelayed({
                checkForSaveUser()
            }, DELAY_TIME)

    private fun checkForSaveUser() {
        val user = preferencesUtil.getUser()
        if (user == null) {
            findNavController().navigate(R.id.action_splashScreenFragment_to_registrationFormFragment)
        } else {
            findNavController().navigate(R.id.action_splashScreenFragment_to_mainScreenFragment)
        }
    }
}

