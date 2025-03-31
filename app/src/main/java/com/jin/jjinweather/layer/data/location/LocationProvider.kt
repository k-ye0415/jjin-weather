package com.jin.jjinweather.layer.data.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.LocationManager
import com.jin.jjinweather.layer.domain.model.location.GeoPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale

class LocationProvider(private val context: Context) {

    suspend fun loadCurrentCityName(latitude: Double, longitude: Double): String {
        return withContext(Dispatchers.IO) {
            try {
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0]
                    "${address.adminArea ?: ""} ${address.subLocality ?: ""}".trim()
                } else {
                    "알 수 없는 위치"
                }
            } catch (e: IOException) {
                "위치 변환 실패"
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun loadCurrentGeoPoint(): Result<GeoPoint> {
        return runCatching {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                ?: throw IllegalStateException("위치를 가져올 수 없습니다.")
            GeoPoint(location.latitude, location.longitude)
        }
    }
}
