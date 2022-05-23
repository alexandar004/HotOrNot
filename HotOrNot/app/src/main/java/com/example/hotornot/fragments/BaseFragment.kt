package com.example.hotornot.fragments

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    abstract fun goToNextScreen()
}