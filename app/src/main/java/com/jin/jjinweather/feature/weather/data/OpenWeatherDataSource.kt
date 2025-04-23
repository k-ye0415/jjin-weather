package com.jin.jjinweather.feature.weather.data

import com.jin.jjinweather.feature.weather.data.model.dto.WeatherDTO
import com.jin.jjinweather.feature.weather.data.model.dto.HistoricalWeatherDTO

interface OpenWeatherDataSource {
    suspend fun fetchWeather(latitude: Double, longitude: Double): WeatherDTO
    suspend fun fetchYesterdayWeather(latitude: Double, longitude: Double): HistoricalWeatherDTO
}
