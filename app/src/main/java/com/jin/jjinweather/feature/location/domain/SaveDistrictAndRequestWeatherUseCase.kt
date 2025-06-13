package com.jin.jjinweather.feature.location.domain

import com.jin.jjinweather.feature.googleplaces.domain.model.District
import com.jin.jjinweather.feature.location.LocationRepository
import com.jin.jjinweather.feature.weather.domain.repository.WeatherRepository

class SaveDistrictAndRequestWeatherUseCase(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(district: District) {
        locationRepository.insertGeoPoint(district.geoPoint)
        locationRepository.insertCityName(district.address)
        weatherRepository.weatherAt(district.geoPoint.latitude, district.geoPoint.longitude)
    }
}
