package com.jin.jjinweather.feature.weather

import com.jin.jjinweather.layer.domain.model.UiState

interface WeatherRepository {
    suspend fun weatherAt(latitude: Double, longitude: Double): UiState<Weather>
}
