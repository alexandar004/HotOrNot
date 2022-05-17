package com.example.hotornot.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
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
        onEmailTextChangeListener()
        binding.btnRegister.setOnClickListener {
            listenForEmptyFields()
        }
        val interests = resources.getStringArray(R.array.interests)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, interests)
        binding.autocomplete.setAdapter(arrayAdapter)
    }

    private fun createUser() {
        val user = User(
            binding.edtFirstName.editText?.text.toString(),
            binding.edtLastName.editText?.text.toString(),
            binding.edtEmail.editText?.text.toString(),
            getSelectRadioBtnValue(), getSelectInterest()
        )
        preferencesUtil.setUser(user)
    }

    private fun onEmailTextChangeListener() {
        binding.editEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.edtEmail.error = isValidEmail()
                binding.btnRegister.isEnabled = true
            }
        })
    }

    private fun isValidEmail(): String? {
        val emailText = binding.editEmail.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            return "Invalid Email Address!"
        }
        return null
    }

    private fun getSelectRadioBtnValue() =
        when (binding.radioGroup.checkedRadioButtonId) {
            R.id.radioBtnMan -> Gender.MALE
            R.id.radioBtnWoman -> Gender.FEMALE
            R.id.radioBtnOther -> Gender.OTHER
            else -> Gender.OTHER
        }

    private fun getSelectInterest(): String {
        return " "
    }

    private fun listenForEmptyFields() {
        binding.btnRegister.setOnClickListener {
            val inputFirstName: String = binding.edtFirstName.editText?.text.toString()
            val inputLastName: String = binding.edtLastName.editText?.text.toString()
            val inputEmail: String = binding.edtEmail.editText?.text.toString()

            if ((inputFirstName.isEmpty()) || (inputLastName.isEmpty()) || (inputEmail.isEmpty())) {
                Toast.makeText(this.context, "Field is required!", Toast.LENGTH_SHORT).show()
            } else {
                createUser()
                findNavController().navigate(R.id.action_registrationFormFragment_to_mainScreenFragment)
            }
        }
    }
}