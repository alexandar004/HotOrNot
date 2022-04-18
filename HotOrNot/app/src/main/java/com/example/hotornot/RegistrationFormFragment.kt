package com.example.hotornot

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.hotornot.databinding.FragmentRegistrationFormBinding

class RegistrationFormFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationFormBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentRegistrationFormBinding.inflate(inflater, container, false)

        val interests = resources.getStringArray(R.array.interests)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, interests)
        binding.autocomplete.setAdapter(arrayAdapter)
        return binding.root
    }
}