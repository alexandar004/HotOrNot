package com.example.hotornot.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.hotornot.data.listener.NoNetworkConnectionListener
import com.example.hotornot.databinding.FragmentNoNetworkConnectionBinding
import com.example.hotornot.viewModel.NoNetworkConnectionViewModel

class NoNetworkConnectionFragment : BaseFragment() {
    lateinit var binding: FragmentNoNetworkConnectionBinding
    private val viewModel: NoNetworkConnectionViewModel by viewModels()
    private val args: NoNetworkConnectionFragmentArgs by navArgs()
    private var noNetworkConnectionListener: NoNetworkConnectionListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNoNetworkConnectionBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noNetworkConnectionListener = args.noNetworkListener
        viewModel.onNetworkConnected.observe(viewLifecycleOwner) {
            if (it) {
                noNetworkConnectionListener?.reloadData()
                findNavController().navigateUp()
            }
        }
    }
}