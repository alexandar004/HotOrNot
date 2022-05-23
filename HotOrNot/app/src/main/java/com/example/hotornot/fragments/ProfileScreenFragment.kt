package com.example.hotornot.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.hotornot.PreferencesUtil
import com.example.hotornot.databinding.FragmentProfileScreenBinding

const val TYPE_IMAGE_INTENT = "image/*"
const val EMPTY_STRING = " "

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
        createUserProfile()
        clickImageListener()
    }

    private fun clickImageListener() {
        binding.profileImg.setOnClickListener {
            changeProfilePicture()
            openSomeActivityForResult()
        }
    }

    private fun createUserProfile() {
        preferencesUtil.getUser()
        val user = preferencesUtil.getUser()
        binding.name.text = user?.firstName + EMPTY_STRING + user?.lastName
        binding.email.text = user?.email
        binding.sex.text = user?.gender.toString()
    }

    private fun getProfilePicture() {
        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val data: Intent? = result.data
                    binding.profileImg.setImageURI(data?.data)
                }
            }
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = TYPE_IMAGE_INTENT
        resultLauncher.launch(intent)
    }

    private fun changeProfilePicture() =
        binding.profileImg.setOnClickListener { getProfilePicture() }

    private fun openSomeActivityForResult() {
        val resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val data: Intent? = result.data
                    binding.profileImg.setImageURI(data?.data)
                }
            }
        val intent = Intent(Intent.ACTION_PICK)
        resultLauncher.launch(intent)
    }
}