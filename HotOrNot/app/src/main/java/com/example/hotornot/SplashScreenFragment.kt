package com.example.hotornot

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

private const val DELAY_TIME = 2000L

class SplashScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goToNextScreenWithDelay()
    }

    private fun goToNextScreenWithDelay() =
        Handler(Looper.getMainLooper())
            .postDelayed({
                goToNextScreen1()
            }, DELAY_TIME)


    private fun goToNextScreen() =
        findNavController().navigate(R.id.action_splashScreenFragment_to_mainScreenFragment)

    private fun goToNextScreen1() =
        findNavController().navigate(R.id.action_splashScreenFragment_to_registrationFormFragment)
}