package com.jin.jjinweather.layer.domain.usecase

import com.jin.jjinweather.layer.domain.model.UiState
import com.jin.jjinweather.feature.weather.Weather
import com.jin.jjinweather.feature.location.LocationRepository
import com.jin.jjinweather.feature.weather.WeatherRepository

class GetCurrentLocationWeatherUseCase(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(): UiState<Weather> {
        val geoPoint = locationRepository.currentGeoPoint()
        return weatherRepository.weatherAt(geoPoint.latitude, geoPoint.longitude)
    }
}
