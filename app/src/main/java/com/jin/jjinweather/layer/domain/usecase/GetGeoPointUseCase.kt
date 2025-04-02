package com.jin.jjinweather.layer.domain.usecase

import com.jin.jjinweather.layer.domain.model.location.GeoPoint
import com.jin.jjinweather.layer.domain.repository.LocationRepository

class GetGeoPointUseCase(private val repository: LocationRepository) {
    suspend operator fun invoke( ) = repository.loadCurrentGeoPoint().fold(
        onSuccess = { GeoPoint(it.latitude, it.longitude) },
        onFailure = { GeoPoint(27.0, 126.0) } // 실패시 기본 값 적용
    )

}
