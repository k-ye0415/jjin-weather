package com.jin.jjinweather.layer.data.repository

import com.jin.jjinweather.layer.data.location.LocationProvider
import com.jin.jjinweather.layer.data.weather.WeatherDataSource
import com.jin.jjinweather.layer.domain.model.weather.Weather
import com.jin.jjinweather.layer.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val weatherDataSource: WeatherDataSource
) : WeatherRepository {

    override suspend fun loadWeather(latitude: Double, longitude: Double): Result<Weather> {
        val loadWeather =  weatherDataSource.loadWeather(latitude, longitude)
        return if (loadWeather == null) {
            Result.failure(Exception("Error!!!")) // 실패시 처리는 DB 생성 후 처리 예정.
        } else {
            Result.success(loadWeather)
        }
    }

    companion object {
        private const val TAG = "WeatherRepository"
    }
}
