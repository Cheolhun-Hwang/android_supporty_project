package com.hooneys.mysupportyproject.MyLocation

import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log

class LocationDAO : LocationListener{
    private val tag : String = "Location Listener"
    private val MIN_DISTANCE_CHANGE_FOR_UPDATE : Float = 0f
    private val MIN_TIME_UPDATES : Long = 1000 * 1 * 1
    private lateinit var manager : LocationManager
    var location: Location? = null

    constructor(m: LocationManager){
        this.manager = m
    }

    fun checkProvider(): Boolean{
        Log.d(tag, "check Provider ...")
        when{
            manager.isProviderEnabled(LocationManager.GPS_PROVIDER) -> return true
            manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) -> return true
            else -> return false
        }
    }

    fun stopListner():Unit{
        manager.removeUpdates(this)
    }

    @SuppressLint("MissingPermission")
    fun start(): Unit {
        when{
            manager.isProviderEnabled(LocationManager.GPS_PROVIDER)->{
                manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATE,
                    this)
            }
            manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)->{
                manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATE,
                    this)
            }
        }

        when{
            manager.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null -> {
                location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                return
            }
            manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) != null -> {
                location = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                return
            }
        }
    }




    override fun onLocationChanged(location: Location?) {
        this.location = location
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
    }

    override fun onProviderEnabled(provider: String?) {
    }

    override fun onProviderDisabled(provider: String?) {
    }

}