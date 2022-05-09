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

class SplashScreen : Fragment()
//    , CommunicationBetweenScreens
{

    private lateinit var binding: FragmentSplashScreenBinding

    //    private val sharedPref = SaveUser()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
//        sharedPref.method1()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goToNextScreenWithDelay()
    }

    private fun goToNextScreenWithDelay() =
        Handler(Looper.getMainLooper())
            .postDelayed({
                findNavController().navigate(R.id.action_splashScreenFragment_to_registrationFormFragment)
            }, DELAY_TIME)

//    override fun goToNextScreen() =
//        findNavController().navigate(R.id.action_splashScreenFragment_to_registrationFormFragment)
}

interface CommunicationBetweenScreens {
    fun goToNextScreen()
}

