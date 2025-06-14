package com.jin.jjinweather.feature.location.domain

import com.jin.jjinweather.feature.googleplaces.domain.model.District
import com.jin.jjinweather.feature.location.LocationRepository
import com.jin.jjinweather.feature.weather.domain.repository.WeatherRepository

class SaveDistrictAndRequestWeatherUseCase(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(pageNumber: Int, district: District) {
        val geoPoint = district.geoPoint.copy(pageNumber = pageNumber)
        locationRepository.insertGeoPoint(geoPoint)
        locationRepository.insertCityName(pageNumber, district.address)
        weatherRepository.weatherAt(pageNumber, district.geoPoint.latitude, district.geoPoint.longitude)
    }
}
