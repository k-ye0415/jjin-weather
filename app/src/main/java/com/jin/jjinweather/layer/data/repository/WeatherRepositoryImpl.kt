package com.jin.jjinweather.layer.data.repository

import com.jin.jjinweather.layer.data.database.dao.WeatherDAO
import com.jin.jjinweather.layer.data.weather.WeatherDataSource
import com.jin.jjinweather.layer.data.weather.toEntityModel
import com.jin.jjinweather.layer.domain.model.weather.Weather
import com.jin.jjinweather.layer.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val weatherDAO: WeatherDAO,
    private val weatherDataSource: WeatherDataSource
) : WeatherRepository {

    override suspend fun loadWeather(latitude: Double, longitude: Double): Result<Weather> {
        val loadWeather = weatherDataSource.loadWeather(latitude, longitude)
        return if (loadWeather == null) {
            Result.failure(Exception("Error!!!")) // 실패시 처리는 DB 생성 후 처리 예정.
        } else {
            Result.success(loadWeather)
        }
    }

    override suspend fun insertWeather(weather: Weather, latitude: Double, longitude: Double) {
        weatherDAO.insert(weather.toEntityModel(latitude, longitude))
    }

    companion object {
        private const val TAG = "WeatherRepository"
    }
}
