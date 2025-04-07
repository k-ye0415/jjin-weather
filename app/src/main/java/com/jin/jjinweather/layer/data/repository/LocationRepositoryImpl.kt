package com.jin.jjinweather.layer.data.repository

import com.jin.jjinweather.layer.data.database.dao.GeoPointDAO
import com.jin.jjinweather.layer.data.database.entity.GeoPointEntity
import com.jin.jjinweather.layer.data.location.LocationProvider
import com.jin.jjinweather.layer.domain.model.location.GeoPoint
import com.jin.jjinweather.layer.domain.repository.LocationRepository

class LocationRepositoryImpl(private val geoPointDAO: GeoPointDAO, private val locationProvider: LocationProvider) :
    LocationRepository {
    override suspend fun loadCurrentGeoPoint(): Result<GeoPoint> = locationProvider.loadCurrentGeoPoint()

    override suspend fun insertGeoPoint(latitude: Double, longitude: Double) {
        geoPointDAO.insert(GeoPointEntity(latitude = latitude, longitude = longitude))
    }

    override suspend fun fetchLastGeoPoint() = geoPointDAO.fetchLastGeoPoint()

}
