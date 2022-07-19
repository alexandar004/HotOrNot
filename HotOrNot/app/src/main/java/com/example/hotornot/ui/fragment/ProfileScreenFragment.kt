package com.example.hotornot.ui.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.viewModels
import com.example.hotornot.databinding.FragmentProfileScreenBinding
import com.example.hotornot.viewModel.ProfileScreenFragmentViewModel

private const val INTENT_TYPE = "image/*"

class ProfileScreenFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileScreenBinding
    private val viewModel: ProfileScreenFragmentViewModel by viewModels()
    private lateinit var registerForActivityResult: ActivityResultLauncher<Intent>
    private lateinit var requestMultiplePermissions: ActivityResultLauncher<Array<String>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileScreenBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        registerForActivityResult()
        registerForPermissionsResult()
        binding.imgProfile.setOnClickListener { checkPermissionForImage(requireContext()) }
    }

    private fun registerForPermissionsResult() {
        requestMultiplePermissions =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                if (permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == true) {
                    pickImageFromGallery()
                }
            }
    }

    private fun checkPermissionForImage(context: Context) =
        if ((checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED)
        ) {
            requestMultiplePermissions.launch(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))
        } else {
            pickImageFromGallery()
        }

    private fun registerForActivityResult() {
        registerForActivityResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val data: Intent? = result.data
                    binding.imgProfile.setImageURI(data?.data)
                }
            }
    }

    private fun pickImageFromGallery() = registerForActivityResult.launch(getGalleryIntent())

    private fun getGalleryIntent(): Intent {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = INTENT_TYPE
        return intent
    }

    private fun observeData() {
        viewModel.navigationLiveData.observe(viewLifecycleOwner) { openScreen(it) }
        viewModel.userLiveData.observe(viewLifecycleOwner) { binding.uiModel = it }
    }
}