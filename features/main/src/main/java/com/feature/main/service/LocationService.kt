package com.feature.main.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.app.data.model.LocationModel
import com.google.android.gms.location.*
import com.google.firebase.database.*
import com.lib.utils.DateUtils

class LocationService : Service() {
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private var mLocationCallback: LocationCallback? = null
    private var mDeviceId: String = ""
    private var mRefTrack :DatabaseReference?=null
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                val locationModel = LocationModel()
                locationModel.lat = locationResult?.lastLocation?.latitude
                locationModel.long = locationResult?.lastLocation?.longitude
                locationModel.date = DateUtils.getCurrentDate().time
                mRefTrack?.push()?.setValue(locationModel)
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            mDeviceId = it.getStringExtra("DEVICE_ID")
        }
        mRefTrack = FirebaseDatabase.getInstance().getReference("track").child(mDeviceId)
        requestLocation()
        return START_STICKY
    }

    private fun requestLocation() {
        val requestLocation = LocationRequest()
        requestLocation.interval = 5000
        requestLocation.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mFusedLocationProviderClient?.requestLocationUpdates(
            requestLocation,
            mLocationCallback,
            null
        )
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}