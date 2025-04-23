package com.jin.jjinweather.feature.weather.domain.usecase

import com.jin.jjinweather.feature.location.LocationRepository
import com.jin.jjinweather.feature.weather.domain.model.Weather
import com.jin.jjinweather.feature.weather.domain.repository.WeatherRepository

class GetCurrentLocationWeatherUseCase(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(): Result<Weather> {
        val geoPoint = locationRepository.currentGeoPoint()
        return weatherRepository.weatherAt(geoPoint.latitude, geoPoint.longitude)
    }
}
