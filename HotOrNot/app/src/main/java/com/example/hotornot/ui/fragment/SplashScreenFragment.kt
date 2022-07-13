package com.example.hotornot.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.hotornot.data.repository.UserRepository
import com.example.hotornot.data.viewModel.SplashScreenViewModel
import com.example.hotornot.databinding.FragmentSplashScreenBinding

private const val DELAY_TIME_IN_MILLIS = 4000L

class SplashScreen : BaseFragment() {

    private val viewModel: SplashScreenViewModel by viewModels()
    private lateinit var binding: FragmentSplashScreenBinding
    private lateinit var userRepository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userRepository = UserRepository.getInstance(view.context)
        viewModel.onLoadUser()
        goToNextScreenWithDelay()
    }

    private fun goToNextScreenWithDelay() =
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.navigationLiveData.observe(viewLifecycleOwner) {
                openScreen(it)
            }
            viewModel.loadFriends()
        }, DELAY_TIME_IN_MILLIS)
}