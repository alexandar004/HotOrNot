package com.example.hotornot.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.hotornot.databinding.FragmentProfileScreenBinding
import com.example.hotornot.viewModel.ProfileScreenFragmentViewModel

private const val ANDROID_VERSION = 28

class ProfileScreenFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileScreenBinding
    private val viewModel: ProfileScreenFragmentViewModel by viewModels()

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
        binding.profileImg.setOnClickListener { getImage() }
    }

    private fun getImage() {
        activity.let {
            if (it?.let { it1 ->
                    ContextCompat.checkSelfPermission(it1.applicationContext,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                } != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            } else {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 2)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, 2)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var selectImage: Uri? = null
        var bitmap: Bitmap?
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {
            selectImage = data.data
        }
        try {
            context.let {
                if (selectImage != null) {
                    if (Build.VERSION.SDK_INT >= ANDROID_VERSION) {
                        val source = it?.let { it ->
                            ImageDecoder.createSource(it.contentResolver,
                                selectImage)
                        }
                        bitmap = source?.let { it1 -> ImageDecoder.decodeBitmap(it1) }
                        binding.profileImg.setImageBitmap(bitmap)

                    } else {
                        if (it != null) {
                            bitmap =
                                MediaStore.Images.Media.getBitmap(it.contentResolver, selectImage)
                            binding.profileImg.setImageBitmap(bitmap)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun observeData() {
        viewModel.navigationLiveData.observe(viewLifecycleOwner) {
            openScreen(it)
        }

        viewModel.userLiveData.observe(viewLifecycleOwner){
            binding.uiModel = it
        }
    }
}