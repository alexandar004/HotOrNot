package com.example.hotornot.ui.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.hotornot.databinding.FragmentSplashScreenBinding
import com.example.hotornot.viewModel.SplashScreenViewModel

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

        Log.d("AAA", "${isNetworkAvailable(requireContext())}")


    }

    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }


    private fun observeData() {

        if (isNetworkAvailable(requireContext())) {
            Handler(Looper.getMainLooper()).postDelayed({
                viewModel.navigationLiveData.observe(viewLifecycleOwner) {
                    openScreen(it)
                }
            }, DELAY_TIME_IN_MILLIS)
        } else {
            openScreen(SplashScreenDirections.actionSplashScreenFragmentToNoNetworkConnectionScreen())
        }

    }
}