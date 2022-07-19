package com.example.hotornot.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.hotornot.viewModel.SplashScreenViewModel
import com.example.hotornot.databinding.FragmentSplashScreenBinding

private const val DELAY_TIME_IN_MILLIS = 4000L

class SplashScreen : BaseFragment() {

    private val viewModel: SplashScreenViewModel by viewModels()
    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.onLoadData()
        observeData()
    }

    private fun observeData() =
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.navigationLiveData.observe(viewLifecycleOwner) {
                openScreen(it)
            }
        }, DELAY_TIME_IN_MILLIS)
}