package com.jin.jjinweather.layer.data.repository

import com.jin.jjinweather.layer.data.location.LocationProvider
import com.jin.jjinweather.layer.domain.model.location.GeoPoint
import com.jin.jjinweather.layer.domain.repository.LocationRepository

class LocationRepositoryImpl(private val locationProvider: LocationProvider) : LocationRepository {
    override suspend fun loadCurrentGeoPoint(): Result<GeoPoint> = locationProvider.loadCurrentGeoPoint()
}
