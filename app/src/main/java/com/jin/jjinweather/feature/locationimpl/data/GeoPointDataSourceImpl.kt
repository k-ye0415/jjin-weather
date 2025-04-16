package com.jin.jjinweather.feature.locationimpl.data

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.location.LocationManager
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.jin.jjinweather.R
import com.jin.jjinweather.feature.location.data.GeoPointDataSource
import com.jin.jjinweather.layer.domain.model.location.GeoPoint

class GeoPointDataSourceImpl(context: Context) : GeoPointDataSource {
    private val context = context.applicationContext

    @RequiresPermission(anyOf = [ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION])
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
