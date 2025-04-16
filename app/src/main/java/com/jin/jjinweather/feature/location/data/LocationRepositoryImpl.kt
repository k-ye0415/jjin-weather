package com.jin.jjinweather.feature.location.data

import com.jin.jjinweather.feature.location.LocationRepository
import com.jin.jjinweather.layer.data.database.entity.GeoPointEntity
import com.jin.jjinweather.layer.domain.model.location.GeoPoint

class LocationRepositoryImpl(
    private val geoPointTrackingDataSource: GeoPointTrackingDataSource,
    private val locationProvider: LocationProvider
) :
    LocationRepository {
    override suspend fun currentGeoPoint(): GeoPoint {
        return locationProvider.currentGeoPoint().fold(
            onSuccess = {
                geoPointTrackingDataSource.insertGeoPoint(
                    GeoPointEntity(
                        latitude = it.latitude,
                        longitude = it.longitude
                    )
                )
                GeoPoint(it.latitude, it.longitude)
            },
            onFailure = {
                val geoPoint = geoPointTrackingDataSource.findLatestGeoPoint()
                if (geoPoint != null) GeoPoint(geoPoint.latitude, geoPoint.longitude)
                else GeoPoint(DEFAULT_LAT, DEFAULT_LNG)
            }
        )
    }

    private companion object {
        const val DEFAULT_LAT = 37.5
        const val DEFAULT_LNG = 126.9
    }
}
