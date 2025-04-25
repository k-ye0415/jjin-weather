package com.jin.jjinweather.feature.weather.domain.usecase

import com.jin.jjinweather.feature.location.LocationRepository
import com.jin.jjinweather.feature.weather.domain.model.CityWeather
import com.jin.jjinweather.feature.weather.domain.repository.WeatherRepository

class GetCurrentLocationWeatherUseCase(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(): Result<CityWeather> {
        val geoPoint = locationRepository.currentGeoPoint()
        val weather = weatherRepository.weatherAt(geoPoint.latitude, geoPoint.longitude)
        return weather.map {
            val cityName = locationRepository.findCityNameAt(geoPoint)
            CityWeather(cityName, it)
        }
    }
}
