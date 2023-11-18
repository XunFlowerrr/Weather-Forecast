package com.example.weatherapp.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object PermissionHelper {
    private val listeners = ArrayList<Listener>()

    fun addListener(listener: Listener) {
        listeners.add(listener)
    }

    fun removeListener(listener: Listener) {
        listeners.remove(listener)
    }

    fun notifyPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResult: IntArray
    ) {
        listeners.forEach { listener ->
            listener.onResult(requestCode, permissions, grantResult)
        }
    }

    interface Listener {
        fun onResult(requestCode: Int, permissions: Array<out String>, grantResult: IntArray)
    }
}

suspend fun Activity.ensurePermissionsGranted(permissions: Array<String>) =
    suspendCoroutine { continuation ->
        val allPermissionGranted = permissions.map { permission ->
            checkSelfPermission(permission)
        }.all { it == PackageManager.PERMISSION_GRANTED }

        if (allPermissionGranted) {
            continuation.resume(null)
            return@suspendCoroutine
        }

        val requestPermissionCode = (Math.random() * 1000).toInt()

        /**
         * app will caches if either of these permissions were not granted
         * Todo: handle [PermissionNotGrantedException]
         */
        requestPermissions(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            requestPermissionCode
        )

        PermissionHelper.addListener(object : PermissionHelper.Listener {
            override fun onResult(
                requestCode: Int,
                permissions: Array<out String>,
                grantResult: IntArray
            ) {
                if (requestCode != requestPermissionCode) return
                PermissionHelper.removeListener(this)

                val somePermissionWasDenied = grantResult.any {
                    it == PackageManager.PERMISSION_DENIED
                }

                if (somePermissionWasDenied) {
                    continuation.resumeWithException(PermissionNotGrantedException)
                } else {
                    continuation.resume(null)
                }
            }
        })
    }

object PermissionNotGrantedException : Exception()