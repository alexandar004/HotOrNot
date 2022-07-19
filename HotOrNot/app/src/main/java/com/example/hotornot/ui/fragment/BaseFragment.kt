package com.example.hotornot.ui.fragment

import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

abstract class BaseFragment : Fragment() {
    
    protected fun openScreen(screenId: NavDirections) =
        findNavController().navigate(screenId)

    protected fun showMessage(message: String) =
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            Toast.makeText(
                requireContext(),
                message,
                Toast.LENGTH_SHORT
            ).show()
        }
}