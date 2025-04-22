package com.jin.jjinweather.feature.weather.domain.repository

import com.jin.jjinweather.feature.weather.domain.model.Weather
import com.jin.jjinweather.feature.weather.ui.state.UiState

interface WeatherRepository {
    suspend fun weatherAt(latitude: Double, longitude: Double): UiState<Weather>
}
