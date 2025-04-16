package com.jin.jjinweather.feature.location.data

import com.jin.jjinweather.layer.data.database.entity.GeoPointEntity
import com.jin.jjinweather.layer.data.location.LocationProvider
import com.jin.jjinweather.layer.domain.model.location.GeoPoint
import com.jin.jjinweather.feature.location.LocationRepository

class LocationRepositoryImpl(
    private val geoPointDAO: GeoPointDAO,
    private val locationProvider: LocationProvider
) :
    LocationRepository {
    override suspend fun currentGeoPoint(): GeoPoint {
        return locationProvider.findCurrentGeoPoint().fold(
            onSuccess = {
                geoPointDAO.insertGeoPoint(GeoPointEntity(latitude = it.latitude, longitude = it.longitude))
                GeoPoint(it.latitude, it.longitude)
            },
            onFailure = {
                val geoPoint = geoPointDAO.findLatestGeoPoint()
                if (geoPoint != null) GeoPoint(geoPoint.latitude, geoPoint.longitude)
                else GeoPoint(DEFAULT_LAT, DEFAULT_LNG)
            }
        )
    }

    companion object {
        private const val DEFAULT_LAT = 37.5
        private const val DEFAULT_LNG = 126.9
    }
}
