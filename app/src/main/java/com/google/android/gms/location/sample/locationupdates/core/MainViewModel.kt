package com.google.android.gms.location.sample.locationupdates.core

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.sample.locationupdates.MainActivity

class MainViewModel : ViewModel() {

    private var _latitude: MutableLiveData<Double> = MutableLiveData()
    val latitude: LiveData<Double> get() = _latitude

    private var _longitude: MutableLiveData<Double> = MutableLiveData()
    val longitude: LiveData<Double> get() = _longitude

    private var _lastUpdateTime: MutableLiveData<String> = MutableLiveData()
    val lastUpdateTime: LiveData<String> get() = _lastUpdateTime

    private var _startButtonEnabled: MutableLiveData<Boolean> = MutableLiveData()
    val startButtonEnabled: LiveData<Boolean> get() = _startButtonEnabled

    private var _stopButtonEnabled: MutableLiveData<Boolean> = MutableLiveData()
    val stopButtonEnabled: LiveData<Boolean> get() = _stopButtonEnabled

    private var _location: MutableLiveData<Location> = MutableLiveData()

    fun setLatitude(latitude: Double) {
        _latitude.value = latitude
    }

    fun setLongitude(longitude: Double) {
        _longitude.value = longitude
    }

    fun setLastUpdateTime(lastUpdateTime: String) {
        _lastUpdateTime.value = lastUpdateTime
    }

    fun setButtonStartEnabled(buttonsEnabled: Boolean) {
        _startButtonEnabled.value = buttonsEnabled
    }

    fun setButtonStopEnabled(buttonsEnabled: Boolean) {
        _stopButtonEnabled.value = buttonsEnabled
    }

    fun startUpdatesButtonHandler(context: Context) {
        if (!MainActivity.mRequestingLocationUpdates) {
            MainActivity.mRequestingLocationUpdates = true
            refreshButtonsState()
            val foregroundIntent = Intent(context, LocationForegroundService::class.java)
            context.startForegroundService(foregroundIntent)

        }
    }
    fun refreshButtonsState() {
        if (MainActivity.mRequestingLocationUpdates) {
            setButtonStartEnabled(false)
            setButtonStopEnabled(true)
        } else {
            setButtonStartEnabled(true)
            setButtonStopEnabled(false)
        }
    }
    /**
     * Handles the Stop Updates button, and requests removal of location updates.
     */
    fun stopUpdatesButtonHandler(context: Context) {
        val serviceIntent = Intent(context, LocationForegroundService::class.java)
        context.stopService(serviceIntent)
        MainActivity.mRequestingLocationUpdates = false
        refreshButtonsState()
    }

    fun setCurrentLocation(
        context: MainActivity, mFusedLocationClient: FusedLocationProviderClient
    ) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mFusedLocationClient.lastLocation.addOnSuccessListener { location ->
            // Got last known location. In some rare situations this can be null.
            setLatitude(
                location?.latitude!!
            )
            setLongitude(
                location.longitude
            )
        }
        .addOnFailureListener {
            context.showSnackbar("Failed on getting current location")
        }
    }

}