package com.example.hotornot.viewModel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.example.hotornot.data.model.User
import com.example.hotornot.data.repository.UserRepository
import com.example.hotornot.ui.fragment.ProfileScreenFragmentDirections

private const val MIN_TIME_MS = 0L
private const val MIN_DISTANCE_M = 0f

class ProfileScreenFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var userRepository: UserRepository
    private  var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private val _navigationLiveData = MutableLiveData<NavDirections>()
    val navigationLiveData: LiveData<NavDirections> = _navigationLiveData
    private val _userLiveData = MutableLiveData<User?>()
    val userLiveData: LiveData<User?> = _userLiveData
    private val _currentLocationLiveData = MutableLiveData<Location>()
    val currentLocationLiveData: LiveData<Location> = _currentLocationLiveData

    init {
        initializedData()
        getUser()
        initLocationListener()
        locationManager =
            getApplication<Application>().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private fun initializedData() {
        userRepository = UserRepository(getApplication())
    }

    fun navigateBtnChangeLocation() {
        val navDirection =
            ProfileScreenFragmentDirections.actionProfileScreenFragmentToLocationScreen()
        _navigationLiveData.postValue(navDirection)
    }

    @SuppressLint("MissingPermission")
    fun findCurrentLocation() {
        val context = getApplication<Application>() ?: return
        if ((ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            return
        }
        val location: Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location != null) {
            _currentLocationLiveData.postValue(location!!)
        } else {
            try {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_MS,
                    MIN_DISTANCE_M,
                    locationListener
                )
            } catch (ex: SecurityException) {
            }
        }
    }

    private fun initLocationListener() {
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                _currentLocationLiveData.postValue(location)
                locationManager.removeUpdates(this)
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }
    }

    private fun getUser() {
        val user = userRepository.getUser()
        _userLiveData.value = user
    }
}