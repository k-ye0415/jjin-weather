package com.jin.jjinweather.feature.weather.data

import com.jin.jjinweather.layer.domain.model.weather.Weather

interface WeatherDataSource {
    suspend fun requestWeatherAt(latitude: Double, longitude: Double): Result<Weather>
    suspend fun requestYesterdayWeatherAt(latitude: Double, longitude: Double): Double?
}
