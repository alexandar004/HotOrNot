package com.example.hotornot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hotornot.databinding.FragmentProfileScreenBinding

class ProfileScreenFragment : Fragment() {

    private lateinit var binding: FragmentProfileScreenBinding
    private lateinit var preferencesUtil: PreferencesUtil

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileScreenBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencesUtil = PreferencesUtil.getInstance(view.context)

        setFriendProfile()
    }

    private fun setFriendProfile() {
        preferencesUtil.getUser()
        val user2 = preferencesUtil.getUser()
        user2?.firstName
        user2?.lastName
        user2?.email
        user2?.gender
        val user = user2?.let { User(it.firstName,user2.lastName,user2.email, user2.gender) }
        if (user != null) {
            preferencesUtil.setUser(user)
        }
    }
}