package com.example.hotornot.ui.fragment

import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

abstract class BaseFragment : Fragment() {
    protected fun openScreen(screenId: NavDirections) =
        findNavController().navigate(screenId)
}