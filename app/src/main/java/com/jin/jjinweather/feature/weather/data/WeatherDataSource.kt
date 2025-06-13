package com.jin.jjinweather.feature.weather.data

import com.jin.jjinweather.feature.weather.domain.model.Weather

interface WeatherDataSource {
    suspend fun requestWeatherAt(pageNumber: Int, latitude: Double, longitude: Double): Result<Weather>
    suspend fun requestYesterdayWeatherAt(latitude: Double, longitude: Double): Double?
}
