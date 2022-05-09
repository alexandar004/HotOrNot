package com.example.hotornot

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hotornot.databinding.FragmentRegistrationFormBinding

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
            createUser()
            findNavController().navigate(R.id.action_registrationFormFragment_to_mainScreenFragment)
        }
        val interests = resources.getStringArray(R.array.interests)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, interests)
        binding.autocomplete.setAdapter(arrayAdapter)
    }

    fun createUser() {
        val user = User(binding.edtFirstName.editText?.text.toString(),
            binding.edtLastName.editText?.text.toString(),
            binding.edtEmail.editText?.text.toString(),
            getSelectRadioBtnValue())
        val userFirstName = user.firstName
        Log.d("HHH", "$userFirstName userFirstName")
        preferencesUtil.setUser(user)
        val user2 = preferencesUtil.getUser()
        user2?.firstName
    }

    private fun getSelectRadioBtnValue() =
        when (binding.radioGroup.checkedRadioButtonId) {
            R.id.radioBtnMan -> Gender.MALE
            R.id.radioBtnWoman -> Gender.FEMALE
            R.id.radioBtnOther -> Gender.OTHER
            else -> Gender.OTHER
        }
}