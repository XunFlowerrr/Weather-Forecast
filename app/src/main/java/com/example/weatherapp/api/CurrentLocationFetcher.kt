package com.example.weatherapp.api

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.weatherapp.activities.MainActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.location.LocationRequest.Builder
import okhttp3.internal.wait

class CurrentLocationFetcher(private val context: MainActivity) {
    val fuesedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    fun fetchLastLocation(lastLocationCallback: (String?) -> Unit) {

        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,1000)
            .setMaxUpdateDelayMillis(100)
            .setWaitForAccurateLocation(false)
            .build()

        val locationCallback = object : LocationCallback()
        {
            override fun onLocationResult(p0: LocationResult) {
//                super.onLocationResult(p0)
                if (p0 != null) {
                    Log.d("CurrentLocationFetcher", "Location: ${p0.lastLocation}")
                    return
                }
            }
        }

        if ((ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                context,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                101
            )
            return
        }
        LocationServices.getFusedLocationProviderClient(context)
            .requestLocationUpdates(locationRequest, locationCallback,null)

        fuesedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                Log.d("CurrentLocationFetcher", "Location: $location")
                lastLocationCallback("${location.latitude},${location.longitude}")
            }
        }

    }

}
