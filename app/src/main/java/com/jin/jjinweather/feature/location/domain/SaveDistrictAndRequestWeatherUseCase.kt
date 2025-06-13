package com.jin.jjinweather.feature.location.domain

import com.jin.jjinweather.feature.googleplaces.domain.model.District
import com.jin.jjinweather.feature.location.LocationRepository

class SaveDistrictAndRequestWeatherUseCase(private val locationRepository: LocationRepository) {
    suspend operator fun invoke(district: District) {
        locationRepository.insertGeoPoint(district.geoPoint)
        locationRepository.insertCityName(district.address)
    }
}
