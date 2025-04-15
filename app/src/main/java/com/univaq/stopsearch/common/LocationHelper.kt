package com.univaq.stopsearch.common

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.content.ContextCompat


class LocationHelper (
    private val context: Context,
    private val onLocationChanged: (Location) -> Unit
){
    private val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
    private val listener = LocationListener{location ->
        onLocationChanged(location)
    }

    fun start(){
        val isGPSEnabled = manager?.isProviderEnabled(LocationManager.GPS_PROVIDER) ?: false
        val isNetworkEnabled = manager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ?: false

        val isFineGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val isCoarseGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if(isGPSEnabled && isFineGranted){
            manager?.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10f, listener)
        }else if (isNetworkEnabled && isCoarseGranted){
            manager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10f, listener)
        }
    }

    fun stop(){
        manager?.removeUpdates(listener)
    }
}