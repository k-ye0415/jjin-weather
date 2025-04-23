package com.jin.jjinweather.feature.weather.domain.repository

import com.jin.jjinweather.feature.weather.domain.model.Weather

interface WeatherRepository {
    suspend fun weatherAt(latitude: Double, longitude: Double): Result<Weather>
}
