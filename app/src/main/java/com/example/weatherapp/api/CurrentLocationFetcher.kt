package com.example.weatherapp.api

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import com.example.weatherapp.utils.ensurePermissionsGranted
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class CurrentLocationFetcher(private val activity: Activity) {
    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(activity)

    @SuppressLint("MissingPermission")
    suspend fun getLocation(): Location {
        activity.ensurePermissionsGranted(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        return suspendCancellableCoroutine { continuation ->
            val cancellationTokenSource = CancellationTokenSource()
            val task =
                fusedLocationProviderClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    cancellationTokenSource.token
                )

            continuation.invokeOnCancellation {
                cancellationTokenSource.cancel()
            }

            task.addOnFailureListener {
                continuation.resumeWithException(it)
            }

            task.addOnSuccessListener {
                continuation.resume(it)
            }
        }
    }
}

