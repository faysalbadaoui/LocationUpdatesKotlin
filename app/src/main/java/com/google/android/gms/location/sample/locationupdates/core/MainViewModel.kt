package com.google.android.gms.location.sample.locationupdates.core

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private var _latitude: MutableLiveData<Double> = MutableLiveData()
    val latitude: MutableLiveData<Double> get() = _latitude

    private var _longitude: MutableLiveData<Double> = MutableLiveData()
    val longitude: MutableLiveData<Double> get() = _longitude

    private var _lastUpdateTime: MutableLiveData<String> = MutableLiveData()
    val lastUpdateTime: MutableLiveData<String> get() = _lastUpdateTime
    fun setLatitude(latitude: Double) {
        _latitude.value = latitude
    }

    fun setLongitude(longitude: Double) {
        _longitude.value = longitude
    }

    fun setLastUpdateTime(lastUpdateTime: String) {
        _lastUpdateTime.value = lastUpdateTime
    }

}