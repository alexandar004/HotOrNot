package com.example.hotornot.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.hotornot.data.repository.UserRepository
import com.example.hotornot.databinding.FragmentProfileScreenBinding

private const val TYPE_IMAGE_INTENT = "image/*"
const val EMPTY_STRING = " "

class ProfileScreenFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileScreenBinding
    private lateinit var userRepository: UserRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userRepository = UserRepository.getInstance(view.context)
        createUserProfile()
        clickImageListener()
        clickBtnChangeLocationConfirmation()
    }

    private fun clickImageListener() {
        binding.profileImg.setOnClickListener {
            changeProfilePicture()
            openActivityForResult()
        }
    }

    private fun createUserProfile() {
        val user = userRepository.getUser()
        binding.name.text = user?.firstName + EMPTY_STRING + user?.lastName
        binding.email.text = user?.email
        binding.sex.text = user?.gender.toString()
    }

    private fun onActivityResult() {
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
        binding.profileImg.setOnClickListener { onActivityResult() }

    private fun openActivityForResult() {
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

    private fun clickBtnChangeLocationConfirmation() =
        binding.btnChangeLocation.setOnClickListener {
            openScreen(ProfileScreenFragmentDirections
                .actionProfileScreenFragmentToLocationScreen())
        }
}