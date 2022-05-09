package com.example.hotornot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hotornot.databinding.FragmentProfileScreenBinding

class ProfileScreenFragment : Fragment() {

    private lateinit var binding: FragmentProfileScreenBinding
//    private lateinit var preferencesUtil: PreferencesUtil
    private lateinit var registrationFormFragment: RegistrationFormFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileScreenBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        preferencesUtil = PreferencesUtil.getInstance(view.context)

        registrationFormFragment = RegistrationFormFragment()
        registrationFormFragment.createUser()

    }
}