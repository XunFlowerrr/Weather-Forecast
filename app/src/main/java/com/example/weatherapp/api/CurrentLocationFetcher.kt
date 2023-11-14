package com.example.weatherapp.api

import android.Manifest
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

class CurrentLocationFetcher(private val context: MainActivity) {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    fun fetchLocation(fetchResponse: (String?) -> Unit) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                p0?.lastLocation?.let {
                    Log.d("fetchLocation", "Success: ${it.latitude}, ${it.longitude}")
                    stopLocationUpdates()
                    fetchResponse("${it.latitude},${it.longitude}")
                }
            }
        }

        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval =
                10000 // Interval in milliseconds at which you want to receive location updates
        }

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return
        }

        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                Log.d(
                    "fetchLocation",
                    "Last known location: ${location.latitude}, ${location.longitude}"
                )
                fetchResponse("${location.latitude},${location.longitude}")
            } else {
                startLocationUpdates(locationRequest, context)
            }
        }.addOnFailureListener { exception ->
            Log.d("fetchLocation", "Failed to get last known location: ${exception.message}")
            startLocationUpdates(locationRequest, context)
        }
    }

    private fun startLocationUpdates(locationRequest: LocationRequest, context: MainActivity) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    fun init()
    {
        startLocationUpdates(LocationRequest.create(), context)
    }
}