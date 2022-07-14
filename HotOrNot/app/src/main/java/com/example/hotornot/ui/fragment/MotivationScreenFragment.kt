package com.example.hotornot.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import com.example.hotornot.R
import com.example.hotornot.data.viewModel.MotivationScreenViewModel
import com.example.hotornot.databinding.FragmentMotivationScreenBinding

class MotivationScreenFragment : BaseFragment() {

    private lateinit var binding: FragmentMotivationScreenBinding
    private val viewModel: MotivationScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentMotivationScreenBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showMessage((getString(R.string.cant_run_from_yourself)))
        observeData()
    }

    private fun observeData() {
        viewModel.navigationLiveData.observe(viewLifecycleOwner) {
            openScreen(it)
        }
        viewModel.motivationText.observe(viewLifecycleOwner) {
            binding.uiModel = it
        }
    }

    private fun showMessage(message: String) =
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            Toast.makeText(
                requireContext(),
                message,
                Toast.LENGTH_SHORT
            ).show()
        }
}