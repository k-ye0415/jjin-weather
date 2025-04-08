package com.jin.jjinweather.layer.domain.usecase

import com.jin.jjinweather.layer.domain.model.UiState
import com.jin.jjinweather.layer.domain.model.weather.Weather
import com.jin.jjinweather.layer.domain.repository.WeatherRepository

class GetCachedLastWeatherUseCase(private val repository: WeatherRepository) {
    suspend operator fun invoke(): UiState<Weather> {
        return try {
            val weather = repository.fetchLastWeather()
            UiState.Success(weather)
        } catch (e: Exception) {
            UiState.Error("데이터 처리 실패: ${e.message}")
        }

    }
}
