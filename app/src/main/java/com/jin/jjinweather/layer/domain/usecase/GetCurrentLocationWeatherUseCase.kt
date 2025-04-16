package com.jin.jjinweather.layer.domain.usecase

import com.jin.jjinweather.layer.domain.model.UiState
import com.jin.jjinweather.layer.domain.model.weather.Weather
import com.jin.jjinweather.feature.location.GeoPointRepository
import com.jin.jjinweather.layer.domain.repository.WeatherRepository

class GetCurrentLocationWeatherUseCase(
    private val geoPointRepository: GeoPointRepository,
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(): UiState<Weather> {
        val geoPoint = geoPointRepository.currentGeoPoint()
        return weatherRepository.weatherAt(geoPoint.latitude, geoPoint.longitude)
    }
}
