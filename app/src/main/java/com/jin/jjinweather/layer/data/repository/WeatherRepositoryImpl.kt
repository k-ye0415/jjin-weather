package com.jin.jjinweather.layer.data.repository

import com.jin.jjinweather.layer.data.database.dao.WeatherDAO
import com.jin.jjinweather.layer.data.weather.WeatherDataSource
import com.jin.jjinweather.layer.data.weather.toDomainModel
import com.jin.jjinweather.layer.data.weather.toEntityModel
import com.jin.jjinweather.layer.domain.model.weather.Weather
import com.jin.jjinweather.layer.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val weatherDAO: WeatherDAO,
    private val weatherDataSource: WeatherDataSource
) : WeatherRepository {

    override suspend fun loadWeather(latitude: Double, longitude: Double): Result<Weather> {
        val loadWeather = weatherDataSource.loadWeather(latitude, longitude)
        return loadWeather
    }

    override suspend fun insertWeather(weather: Weather):Boolean{
        val result = weatherDAO.insert(weather.toEntityModel())
        return result != -1L
    }


    override suspend fun fetchLastWeather(): Weather {
        val weather = weatherDAO.fetchLastWeather()
        return weather.toDomainModel()
    }

    companion object {
        private const val TAG = "WeatherRepository"
    }
}
