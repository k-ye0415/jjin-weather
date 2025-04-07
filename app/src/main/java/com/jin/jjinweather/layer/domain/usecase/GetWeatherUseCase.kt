package com.jin.jjinweather.layer.domain.usecase

import com.jin.jjinweather.layer.domain.model.UiState
import com.jin.jjinweather.layer.domain.model.weather.Weather
import com.jin.jjinweather.layer.domain.repository.WeatherRepository

class GetWeatherUseCase(private val repository: WeatherRepository) {
    suspend operator fun invoke(latitude: Double, longitude: Double): UiState<Weather> {
        return repository.loadWeather(latitude, longitude).fold(
            onSuccess = { weather ->
                repository.insertWeather(weather)
                UiState.Success(weather)
            },
            onFailure = {
                val lastWeather = repository.fetchLastWeather()
                UiState.Error("데이터 처리 실패: ${it.message}", lastWeather)
            }
        )
    }
}
