package com.jin.jjinweather.layer.domain.usecase

import com.jin.jjinweather.layer.domain.model.location.GeoPoint
import com.jin.jjinweather.layer.domain.repository.LocationRepository

class GetGeoPointUseCase(private val repository: LocationRepository) {
    suspend operator fun invoke() = repository.loadCurrentGeoPoint().fold(
        onSuccess = {
            repository.insertGeoPoint(it.latitude, it.longitude)
            GeoPoint(it.latitude, it.longitude)
        },
        onFailure = {
            val lastGeoPoint = repository.fetchLastGeoPoint()
            GeoPoint(lastGeoPoint.latitude, lastGeoPoint.longitude)
        }
    )

}
