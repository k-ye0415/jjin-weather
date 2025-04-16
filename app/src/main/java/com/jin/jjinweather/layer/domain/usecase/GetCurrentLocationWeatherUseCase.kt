package com.jin.jjinweather.layer.domain.usecase

import com.jin.jjinweather.layer.domain.model.UiState
import com.jin.jjinweather.layer.domain.model.weather.Weather
import com.jin.jjinweather.layer.domain.repository.LocationRepository
import com.jin.jjinweather.layer.domain.repository.WeatherRepository

class GetCurrentLocationWeatherUseCase(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(): UiState<Weather> {
        val geoPoint = locationRepository.currentGeoPoint()
        return weatherRepository.weatherAt(geoPoint.latitude, geoPoint.longitude)
    }
}
