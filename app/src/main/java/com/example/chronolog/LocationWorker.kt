package com.example.chronolog

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat

class LocationWorker(appContext: Context, workerParams: androidx.work.WorkerParameters) :
    androidx.work.Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val fusedLocationClient =
            com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(applicationContext)
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                "android.permission.ACCESS_FINE_LOCATION"
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                applicationContext,
                "android.permission.ACCESS_COARSE_LOCATION"
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return Result.failure()
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                if (location != null) {
                    Log.d("com.example.chronolog.LocationWorker", "Location: ${location.latitude}, ${location.longitude}")
                }
            }
        return Result.success()
    }
}