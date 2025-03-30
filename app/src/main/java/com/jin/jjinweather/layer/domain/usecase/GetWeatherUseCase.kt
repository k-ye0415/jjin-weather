package com.jin.jjinweather.layer.domain.usecase

import com.jin.jjinweather.layer.domain.model.UiState
import com.jin.jjinweather.layer.domain.model.weather.Weather
import com.jin.jjinweather.layer.domain.repository.WeatherRepository

class GetWeatherUseCase(private val repository: WeatherRepository) {
    suspend operator fun invoke(): UiState<Weather> {
        return repository.loadWeather(37.0, 126.0).fold(
            onSuccess = { UiState.Success(it) },
            onFailure = { UiState.Error("데이터 처리 실패: ${it.message}") }
        )
    }
}
