package com.jin.jjinweather.feature.location.data

import android.annotation.SuppressLint
import com.jin.jjinweather.layer.domain.model.location.GeoPoint

interface LocationProvider {
    suspend fun findCityNameAt(latitude: Double, longitude: Double): String

    @SuppressLint("MissingPermission")
    fun findCurrentGeoPoint(): Result<GeoPoint>
}
