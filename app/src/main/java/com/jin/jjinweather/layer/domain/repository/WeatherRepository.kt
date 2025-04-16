package com.jin.jjinweather.layer.domain.repository

import com.jin.jjinweather.layer.domain.model.UiState
import com.jin.jjinweather.layer.domain.model.weather.Weather

interface WeatherRepository {
    suspend fun weatherAt(latitude: Double, longitude: Double): UiState<Weather>
}
