package com.jin.jjinweather.layer.domain.usecase

import com.jin.jjinweather.layer.domain.model.location.GeoPoint
import com.jin.jjinweather.layer.domain.repository.LocationRepository

class GetGeoPointUseCase(private val repository: LocationRepository) {
    suspend operator fun invoke() = repository.loadCurrentGeoPoint().fold(
        onSuccess = { geoPoint ->
            repository.insertGeoPointToLocalDB(geoPoint.latitude, geoPoint.longitude)
            GeoPoint(geoPoint.latitude, geoPoint.longitude)
        },
        onFailure = {
            try {
                val lastGeoPoint = repository.fetchLastGeoPointFromLocalDB()
                GeoPoint(lastGeoPoint.latitude, lastGeoPoint.longitude)
            } catch (e: Exception) {
                GeoPoint(DEFAULT_LAT, DEFAULT_LNG)
            }
        }
    )

    companion object {
        private const val DEFAULT_LAT = 37.5
        private const val DEFAULT_LNG = 126.9
    }
}
