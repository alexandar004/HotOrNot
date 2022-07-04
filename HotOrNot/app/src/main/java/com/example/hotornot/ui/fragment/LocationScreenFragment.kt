package com.example.hotornot.ui.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import androidx.fragment.app.Fragment
import com.example.hotornot.BuildConfig
import com.example.hotornot.R
import com.example.hotornot.databinding.FragmentLocationScreenBinding

private const val START_PAINTED_INDEX = 6
private const val END_PAINTED_INDEX = 8

class LocationScreen : Fragment() {

    private lateinit var binding: FragmentLocationScreenBinding

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

    private fun checkPermissionForLocation(context: Context) {
        if ((ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_DENIED)
        ) {
            val permission = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            requestPermissions(permission, 1)
            Log.d("AAA", "PERMISSION_DENIED")
            isActiveLocation(requireContext())
        } else {
            Log.d("AAA", "PERMISSION_ENABLED")
            repeatAlertDialog()
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
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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
            setMessage("Location permission is needed to track your location current location !")
            setTitle("Permission required")
            setPositiveButton(getText(R.string.ok)) { _, _ ->
                checkPermissionForLocation(requireContext())
            }
            setNegativeButton(getString(R.string.close)) { _, _ ->
                showSadFace()
            }.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (isActiveLocation(requireContext())) {
                showHappyFace()
            } else {
                showSadFace()
            }
        }
    }

    private fun checkForActiveLocation() {
        if (!isActiveLocation(requireContext())) {

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