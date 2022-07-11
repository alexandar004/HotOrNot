package com.example.hotornot.ui.fragment

import android.Manifest
import android.app.AlertDialog
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
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import androidx.navigation.fragment.findNavController
import com.example.hotornot.BuildConfig
import com.example.hotornot.R
import com.example.hotornot.data.repository.FriendRepository
import com.example.hotornot.databinding.FragmentLocationScreenBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import java.util.*

private const val START_PAINTED_INDEX = 6
private const val END_PAINTED_INDEX = 8
private const val LOCATION_INTERVAL = 10000L
private const val LOCATION_FASTEST_INTERVAL = 5000L
private const val REQUEST_CODE = 1

class LocationScreen : BaseFragment() {

    private lateinit var binding: FragmentLocationScreenBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var friendRepository: FriendRepository

    private var locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            val location: Location = p0.lastLocation
            updateAddressUI(location)
        }
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
        friendRepository = FriendRepository.getInstance(view.context)
        spanText()
        clickBtnChangeConfirmation()
    }

    private fun updateAddressUI(location: Location) {
        clearRatedFriends()
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val addressList = geocoder.getFromLocation(location.latitude,
            location.longitude, REQUEST_CODE) as ArrayList<Address>
    }

    private fun getLocation() {
        if (newCheckForLocationPermission())
            updateLocation()
    }

    private fun updateLocation() {
        val locationRequest = LocationRequest()
        locationRequest.apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = LOCATION_INTERVAL
            fastestInterval = LOCATION_FASTEST_INTERVAL
        }
        fusedLocationProviderClient = FusedLocationProviderClient(this.requireContext())
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            showHappyFace()
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.myLooper())
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

    private fun checkPermissionForLocation(context: Context) {
        if ((ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_DENIED)
        ) {
            val permission = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(permission, REQUEST_CODE)
        } else {
            checkLocationEnabled()
        }
    }

    private fun checkLocationEnabled() {
        val gpsEnabled = isActiveLocation(requireContext())
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

    private fun openSettings() =
        startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse(getString(R.string.key) + BuildConfig.APPLICATION_ID)))


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
                newCheckForLocationPermission()
            }
            .setNegativeButton(
                R.string.close
            ) { _, _ ->
                showSadFace()
            }
            .show()
    }

    private fun newCheckForLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    override fun goToNextScreen() =
        findNavController().navigate(R.id.action_locationScreen_to_profileScreenFragment)


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE) {
            if (isActiveLocation(requireContext()))
                showHappyFace()
            else
                showSadFace()
        }
    }

    private fun showHappyFace() {
        val numberOfSuggestion = friendRepository.getAllSavedFriends().size
        binding.sadFaceGroup.visibility = View.VISIBLE
        binding.imgMoodOnFace.setImageResource(R.drawable.ic_happy_face)
        binding.btnSettings.text = getString(R.string.done)
        binding.txtPermission.text =
            getString(R.string.location_successfully_changed) + " " + numberOfSuggestion
        clickBtnDoneConfirmation()
    }

    private fun clickBtnDoneConfirmation() =
        binding.btnSettings.setOnClickListener { goToNextScreen() }

    private fun showSadFace() {
        binding.imgMoodOnFace.setImageResource(R.drawable.ic_sad_face)
        binding.sadFaceGroup.visibility = View.VISIBLE
        onBtnSettingsClicked()
    }

    private fun clearRatedFriends() =
        friendRepository.getAllSavedFriends()

    private fun onBtnSettingsClicked() =
        binding.btnSettings.setOnClickListener { openSettings() }
}