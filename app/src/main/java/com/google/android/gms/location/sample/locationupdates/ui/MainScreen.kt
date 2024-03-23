package com.google.android.gms.location.sample.locationupdates.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.google.android.gms.location.sample.locationupdates.MainActivity
import com.google.android.gms.location.sample.locationupdates.core.MainViewModel

@Composable
fun MainScreen(context: MainActivity, viewModel: MainViewModel) {

    var longitude by remember { mutableDoubleStateOf(0.0) }
    var latitude by remember { mutableDoubleStateOf(0.0) }
    var lastUpdateTime by remember { mutableStateOf("") }
    var startButtonEnabled by remember { mutableStateOf(false) }
    var stopButtonEnabled by remember { mutableStateOf(false) }

    viewModel.startButtonEnabled.observe(context) {
        startButtonEnabled = it
    }

    viewModel.stopButtonEnabled.observe(context) {
        stopButtonEnabled = it
    }

    viewModel.longitude.observe(context) {
        longitude = it
    }

    viewModel.latitude.observe(context) {
        latitude = it
    }

    viewModel.lastUpdateTime.observe(context) {
        lastUpdateTime = it
    }

    Box(modifier = Modifier.fillMaxSize()){
        Column {
            Row {
                Button(onClick = { viewModel.startUpdatesButtonHandler(context) }, enabled = startButtonEnabled) {
                    Text("START UPDATES")
                }
                Button(onClick = { viewModel.stopUpdatesButtonHandler(context) }, enabled = stopButtonEnabled) {
                    Text("STOP UPDATES")
                }
            }
            Text(text = "Latitude: $latitude ")
            Text(text = "Longitude: $longitude")
            Text(text = "Last update time: $lastUpdateTime")
        }
    }

}