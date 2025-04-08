package com.jin.jjinweather.layer.domain.usecase

import com.jin.jjinweather.layer.domain.SaveError
import com.jin.jjinweather.layer.domain.SaveResult
import com.jin.jjinweather.layer.domain.model.weather.Weather
import com.jin.jjinweather.layer.domain.repository.WeatherRepository

class SaveWeatherUseCase(private val repository: WeatherRepository) {
    suspend operator fun invoke(weather: Weather): SaveResult {
        return try {
            val inserted = repository.insertWeather(weather)
            if (inserted) SaveResult.Success
            else SaveResult.Failure(SaveError.DbUnavailable)
        } catch (e: Exception) {
            SaveResult.Failure(SaveError.Unknown(e))
        }
    }
}
