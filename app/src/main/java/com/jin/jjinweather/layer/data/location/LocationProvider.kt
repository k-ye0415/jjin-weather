package com.jin.jjinweather.layer.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.LocationManager
import com.jin.jjinweather.R
import com.jin.jjinweather.layer.domain.model.location.GeoPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale

class LocationProvider(context: Context) {

    private val context = context.applicationContext

    suspend fun loadCurrentCityName(latitude: Double, longitude: Double): String {
        return withContext(Dispatchers.IO) {
            val address = try {
                val geocoder = Geocoder(context, Locale.getDefault())
                geocoder.getFromLocation(latitude, longitude, 1)?.firstOrNull()
            } catch (e: IOException) {
                null
            }

            if (address == null) {
                context.getString(R.string.error_unknown_address)
            } else {
                "${address.adminArea.orEmpty()} ${address.subLocality.orEmpty()}".trim()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun loadCurrentGeoPoint(): Result<GeoPoint> {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = try {
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        } catch (e: Exception) {
            return Result.failure(e)
        }

        return if (location != null) {
            Result.success(GeoPoint(location.latitude, location.longitude))
        } else {
            Result.failure(Exception(context.getString(R.string.error_location_not_found)))
        }
    }
}
