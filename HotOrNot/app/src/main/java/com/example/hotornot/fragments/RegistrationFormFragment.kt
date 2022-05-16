package com.example.hotornot.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hotornot.PreferencesUtil
import com.example.hotornot.R
import com.example.hotornot.User
import com.example.hotornot.databinding.FragmentRegistrationFormBinding
import com.example.hotornot.enums.Gender

class RegistrationFormFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationFormBinding
    private lateinit var preferencesUtil: PreferencesUtil

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRegistrationFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferencesUtil = PreferencesUtil.getInstance(view.context)
        binding.btnRegister.setOnClickListener {

            listenForEmptyFields()
        }

        val interests = resources.getStringArray(R.array.interests)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, interests)
        binding.autocomplete.setAdapter(arrayAdapter)
    }

    private fun createUser() {
        val user = User(binding.edtFirstName.editText?.text.toString(),
            binding.edtLastName.editText?.text.toString(),
            binding.edtEmail.editText?.text.toString(),
            getSelectRadioBtnValue())

        val userFirstName = user.firstName
        Log.d("HHH", "$userFirstName userFirstName")
        preferencesUtil.setUser(user)
        val user2 = preferencesUtil.getUser()
        user2?.firstName
        user2?.lastName
        user2?.email
        user2?.gender
    }

    private fun getSelectRadioBtnValue() =
        when (binding.radioGroup.checkedRadioButtonId) {
            R.id.radioBtnMan -> Gender.MALE
            R.id.radioBtnWoman -> Gender.FEMALE
            R.id.radioBtnOther -> Gender.OTHER
            else -> Gender.OTHER
        }

    private fun listenForEmptyFields() {
        binding.btnRegister.setOnClickListener {
            val input: String = binding.edtFirstName.editText?.text.toString()

            if (input.trim().isNotEmpty()) {
                createUser()
                findNavController().navigate(R.id.action_registrationFormFragment_to_mainScreenFragment)
            } else {
                Toast.makeText(this.context, "Field is required!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}