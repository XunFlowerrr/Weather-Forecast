package com.example.weatherapp.utils

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings

class SettingHelper {
    fun askToEnableLocation(context: Context) {

        val locationManger = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        val isGpsEnabled = locationManger?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false

        if (!isGpsEnabled) {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
}
