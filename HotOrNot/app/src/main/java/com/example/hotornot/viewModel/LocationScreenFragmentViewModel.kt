package com.example.hotornot.viewModel


import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.example.hotornot.data.repository.FriendRepository


private const val MIN_TIME_MS = 0L
private const val MIN_DISTANCE_M = 0F

class LocationScreenFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var friendRepository: FriendRepository

    private var locationManager: LocationManager
    private lateinit var locationListener: LocationListener

    private val _navigateLiveData = MutableLiveData<NavDirections>()
    val navigateLiveData: LiveData<NavDirections> = _navigateLiveData

    private val _findLocationLiveData = MutableLiveData<Location?>()
    val findLocationLiveData: LiveData<Location?> = _findLocationLiveData

    private val _suggestionsLiveData = MutableLiveData<Int>()
    val suggestionsLiveData: LiveData<Int> = _suggestionsLiveData

    init {
        initializedData()
        initLocationListener()
        locationManager =
            getApplication<Application>().getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    fun hasLocationEnabled(): Boolean {
        var gpsEnabled = false
        try {
            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (ex: Exception) {
        }
        return gpsEnabled
    }

    @SuppressLint("MissingPermission")
    fun findLocation() {
        val location: Location? = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        if (location != null) {
            _findLocationLiveData.value = location
            resetRatedFriends()
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
                _findLocationLiveData.postValue(location)
                resetRatedFriends()
                locationManager.removeUpdates(this)
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }
    }

    fun resetRatedFriends() {
        friendRepository.getAllSavedFriends().forEach {
            it.isHot = null
            clearRatedFriends()
        }
    }

    fun getNumberOfSuggestions() {
        val suggestions = friendRepository.getAllSavedFriends().size
        _suggestionsLiveData.postValue(suggestions)
    }


    private fun initializedData() {
        friendRepository = FriendRepository.getInstance(getApplication())
    }

    private fun clearRatedFriends() = friendRepository.getAllSavedFriends()
}