package com.jin.jjinweather.layer.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.jin.jjinweather.R
import com.jin.jjinweather.feature.location.data.GeoCodingDataSource
import com.jin.jjinweather.feature.location.data.LocationProvider
import com.jin.jjinweather.layer.domain.model.location.GeoPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale

class LocationProviderImpl(context: Context) : LocationProvider, GeoCodingDataSource {

    private val context = context.applicationContext

    override suspend fun findCityNameAt(latitude: Double, longitude: Double): String {
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
    override fun currentGeoPoint(): Result<GeoPoint> {
        val locationManager = ContextCompat.getSystemService(context, LocationManager::class.java)
            ?: return Result.failure(Exception(context.getString(R.string.error_location_not_found)))
        val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

        return if (location != null) {
            Result.success(GeoPoint(location.latitude, location.longitude))
        } else {
            Result.failure(Exception(context.getString(R.string.error_location_not_found)))
        }
    }
}
