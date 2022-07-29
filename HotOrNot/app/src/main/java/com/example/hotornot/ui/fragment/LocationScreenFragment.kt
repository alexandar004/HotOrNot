package com.example.hotornot.ui.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import androidx.fragment.app.viewModels
import com.example.hotornot.R
import com.example.hotornot.databinding.FragmentLocationScreenBinding
import com.example.hotornot.viewModel.LocationScreenFragmentViewModel

private const val START_PAINTED_INDEX = 6
private const val END_PAINTED_INDEX = 8

class LocationScreen : BaseFragment() {

    private val viewModel: LocationScreenFragmentViewModel by viewModels()
    private lateinit var binding: FragmentLocationScreenBinding

    private lateinit var registerForActivityResult: ActivityResultLauncher<Intent>
    private lateinit var requestMultiplePermissions: ActivityResultLauncher<Array<String>>

//    private var locationCallback = object : LocationCallback() {
//        override fun onLocationResult(p0: LocationResult) {
//            val location: Location = p0.lastLocation!!
//            updateAddressUI(location)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLocationScreenBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spanText()
        registerForActivityResult()
        binding.btnChange.setOnClickListener {
            checkPermissionForLocation(view.context)
        }
        registerForPermissionsResult()
        observeData()
    }

    private fun checkPermissionForLocation(context: Context) {
        if ((ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_DENIED)
        ) {
            val permission = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            requestMultiplePermissions.launch(permission)
        } else {
            checkForLocationEnabled()
        }
    }

    private fun observeData() {
        viewModel.findLocationLiveData.observe(viewLifecycleOwner) {
            showHappyFace()
            if (it != null) {
                findCountry(it.latitude, it.longitude)
            }
        }
    }

    private fun findCountry(latitude: Double, longitude: Double) {
        val geoCoder = Geocoder(context)
        geoCoder.getFromLocation(latitude, longitude, 5)
    }

    private fun getLocation() {
        val context = context ?: return

        if ((ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        ) {
            return
        }
        viewModel.findLocation()
    }

    private fun registerForActivityResult() {
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (viewModel.hasLocationEnabled()) {
                observeData()
                getLocation()
            } else {
                showSadFace()
            }
        }
    }


    private fun spanText() {
        val spannableString = SpannableString(getString(R.string.lets_go))
        val color = ForegroundColorSpan(Color.BLUE)
        spannableString.setSpan(color,
            START_PAINTED_INDEX,
            END_PAINTED_INDEX,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        binding.txtMotivation.text = spannableString
    }

    private fun registerForPermissionsResult() {
        requestMultiplePermissions =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true)
                    checkForLocationEnabled()
                else
                    repeatAlertDialog()
            }
    }


    private fun checkForLocationEnabled() {
        if (!viewModel.hasLocationEnabled()) {
            AlertDialog.Builder(context)
                .setMessage(R.string.gps_network_not_enabled)
                .setPositiveButton(
                    R.string.permission_denied
                ) { _, _ ->
                    showLottie()
                    registerForActivityResult.launch(Intent(ACTION_LOCATION_SOURCE_SETTINGS))
                }
                .setNegativeButton(
                    R.string.cancel
                ) { _, _ ->
                    showSadFace()
                }
                .show()
        } else
            showHappyFace()
    }

    private fun checkLocationEnabled() {
        val gpsEnabled = viewModel.hasLocationEnabled()
        if (!gpsEnabled) {
            AlertDialog.Builder(context)
                .setMessage(R.string.gps_network_not_enabled)
                .setPositiveButton(
                    R.string.change_location
                ) { _, _ ->
                    showLottie()
                    startActivityForResult(Intent(ACTION_LOCATION_SOURCE_SETTINGS), 1)
                }
                .setNegativeButton(
                    R.string.cancel
                ) { _, _ ->
                    showSadFace()
                }
                .show()
        } else {
            showHappyFace()
        }
    }

    private fun showLottie() {
        binding.motivationGroup.visibility = View.GONE
        binding.navigationLottie.visibility = View.VISIBLE
    }

    private fun isActiveLocation(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        if ((ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)
        )
            checkLocationEnabled()
        else
            repeatAlertDialog()
    }

    private fun repeatAlertDialog() {
        AlertDialog.Builder(context)
            .setMessage(R.string.location_permission)
            .setPositiveButton(
                R.string.ok
            ) { _, _ ->
                checkForLocationPermission()
            }
            .setNegativeButton(
                R.string.close
            ) { _, _ ->
                showSadFace()
            }
            .show()
    }

    private fun checkForLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            if (isActiveLocation(requireContext()))
                showHappyFace()
            else
                showSadFace()
        }
    }

    private fun showHappyFace() {
        val numberOfSuggestion = viewModel.getNumberOfSuggestions()
        binding.sadFaceGroup.visibility = View.VISIBLE
        binding.imgMoodOnFace.setImageResource(R.drawable.ic_happy_face)
        binding.btnSettings.text = getString(R.string.done)
        binding.txtPermission.text =
            getString(R.string.location_successfully_changed) + " " + numberOfSuggestion
        clickBtnDoneConfirmation()
    }

    private fun clickBtnDoneConfirmation() =
        binding.btnSettings.setOnClickListener {
            openScreen(LocationScreenDirections.actionLocationScreenToProfileScreenFragment())
        }

    private fun showSadFace() {
        binding.imgMoodOnFace.setImageResource(R.drawable.ic_sad_face)
        binding.sadFaceGroup.visibility = View.VISIBLE
        onBtnSettingsClicked()
    }

    private fun onBtnSettingsClicked() =
        binding.btnSettings.setOnClickListener { viewModel.openSettings() }
}