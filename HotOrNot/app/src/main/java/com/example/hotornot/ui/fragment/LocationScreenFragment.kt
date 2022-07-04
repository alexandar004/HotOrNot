package com.example.hotornot.ui.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import androidx.fragment.app.Fragment
import com.example.hotornot.BuildConfig
import com.example.hotornot.R
import com.example.hotornot.databinding.FragmentLocationScreenBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import java.util.*
import kotlin.collections.ArrayList

private const val START_PAINTED_INDEX = 6
private const val END_PAINTED_INDEX = 8

class LocationScreen : Fragment() {

    private lateinit var binding: FragmentLocationScreenBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val locationRequestId = 100

    private var locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            var location: Location = p0.lastLocation

            updateAddressUI(location)
        }

        private fun updateAddressUI(location: Location) {
            var addressList = ArrayList<Address>()
            val geocoder = Geocoder(requireContext(), Locale.getDefault())

            addressList = geocoder.getFromLocation(location.latitude,
                location.longitude,
                1) as ArrayList<Address>
        }
    }

    private fun getLocation() {
        if (newCheckForLocationPermission()) {
            updateLocation()
        }
    }

    private fun updateLocation() {
        val locationRequest = LocationRequest()
        locationRequest.apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 10000
            fastestInterval = 5000
        }

        fusedLocationProviderClient = FusedLocationProviderClient(this.requireContext())
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.myLooper())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLocationScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spanText()
        clickBtnChangeConfirmation()
    }

    private fun spanText() {
        val spannableString = SpannableString(getString(R.string.lets_go))
        val color = ForegroundColorSpan(Color.BLUE)
        spannableString.setSpan(color,
            START_PAINTED_INDEX,
            END_PAINTED_INDEX,
            Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
        binding.motivationText.text = spannableString
    }

    private fun checkPermissionForLocation(context: Context): Boolean {
        return if ((ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_DENIED)
        ) {
            val permission = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(permission, 1)
            isActiveLocation(requireContext())
            true
        } else {
            repeatAlertDialog()
            false
        }
    }

    private fun isActiveLocation(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }

    private fun openSettings() {
        startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse(getString(R.string.key) + BuildConfig.APPLICATION_ID)))
    }

    private fun clickBtnChangeConfirmation() {
        binding.btnChange.setOnClickListener {
            checkPermissionForLocation(this.requireContext())
            getLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        if ((ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED)
        ) {
            Log.d(TAG, "onRequestPermissionsResult checkLocationEnabled")
            isActiveLocation(requireContext())
        } else {
            Log.d(TAG, "onRequestPermissionsResult askNicely")
            repeatAlertDialog()
        }
    }

    private fun repeatAlertDialog() {
        val builder = AlertDialog.Builder(requireContext())

        builder.apply {
            setMessage(getString(R.string.location_permission))
            setTitle(getString(R.string.required_permission))
            setPositiveButton(getText(R.string.ok)) { _, _ ->
                checkPermissionForLocation(requireContext())
            }
            setNegativeButton(getString(R.string.close)) { _, _ ->
                showSadFace()
            }.show()
        }
    }

    private fun newCheckForLocationPermission(): Boolean {

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
            return true

        return false

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            if (isActiveLocation(requireContext())) {
                showHappyFace()
            } else {
                showSadFace()
            }
        }
    }

    private fun showHappyFace() {
        binding.imgMoodOnFace.setImageResource(R.drawable.ic_happy_face)
        binding.txtPermission.text = getString(R.string.successfully_changed_location)
        binding.btnSettings.text = getString(R.string.done)
    }

    private fun showSadFace() {
        hideMotivationGroup()
        binding.sadFaceGroup.visibility = View.VISIBLE
        clickBtnSettingsConfirmation()
    }

    private fun hideMotivationGroup() {
        binding.motivationGroup.visibility = View.INVISIBLE
    }

    private fun clickBtnSettingsConfirmation() {
        binding.btnSettings.setOnClickListener {
            openSettings()
        }
    }
}