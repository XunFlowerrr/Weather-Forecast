package com.example.weatherapp.activities

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.weatherapp.R
import com.example.weatherapp.utils.PermissionHelper

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionHelper.notifyPermissionsResult(requestCode, permissions, grantResults)
    }
}
