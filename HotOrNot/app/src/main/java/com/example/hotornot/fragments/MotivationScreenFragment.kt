package com.example.hotornot.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hotornot.R

class MotivationScreenFragment : BaseFragment() {

    private var offlineTimeCounter = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_motivation_screen, container, false)
    }

    override fun goToNextScreen() {
        findNavController().navigate(R.id.action_motivationScreen_to_mainScreenFragment)
    }


//systemCurrentTimeMilis
}