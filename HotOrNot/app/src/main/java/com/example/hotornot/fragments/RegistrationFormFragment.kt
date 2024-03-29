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

const val INVALID_EMAIL_MSG = "Invalid Email Address!"

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
        setSpinnerInterestsMenu()

        checkForEmailValidation()
        clickListenerForButtonRegister()
    }

    private fun clickListenerForButtonRegister() {
        binding.btnRegister.setOnClickListener {
            listenForEmptyFields()
        }
    }

    private fun createUser() {
        val inputFirstName = binding.edtFirstName.editText?.text.toString()
        val inputLastName = binding.edtLastName.editText?.text.toString()
        val inputEmail = binding.edtEmail.editText?.text.toString()

        val user = User(
            inputFirstName,
            inputLastName,
            inputEmail,
            getSelectRadioBtnValue(),
            binding.spinnerMenu.selectedItem.toString()
        )
        preferencesUtil.setUser(user)
    }

    private fun checkForEmailValidation() {
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
            return INVALID_EMAIL_MSG
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

    private fun setSpinnerInterestsMenu() {
        val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(), android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.interests))

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerMenu.adapter = spinnerArrayAdapter
    }

    private fun listenForEmptyFields() {
        binding.btnRegister.setOnClickListener {
            val inputFirstName: String = binding.edtFirstName.editText?.text.toString()
            val inputLastName: String = binding.edtLastName.editText?.text.toString()
            val inputEmail: String = binding.edtEmail.editText?.text.toString()

            if ((inputFirstName.isEmpty()) || (inputLastName.isEmpty()) || (inputEmail.isEmpty())) {
                makeToastForEmptyFields()
            } else {
                createUser()
                findNavController().navigate(R.id.action_registrationFormFragment_to_mainScreenFragment)
            }
        }
    }

    private fun makeToastForEmptyFields() =
        Toast.makeText(this.context, "Field is required!", Toast.LENGTH_SHORT).show()
}