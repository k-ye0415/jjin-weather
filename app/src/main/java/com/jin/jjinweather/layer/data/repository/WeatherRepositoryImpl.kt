package com.jin.jjinweather.layer.data.repository

import com.jin.jjinweather.layer.data.database.RoomDataSource
import com.jin.jjinweather.layer.data.weather.WeatherDataSource
import com.jin.jjinweather.layer.domain.model.weather.Weather
import com.jin.jjinweather.layer.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val roomDataSource: RoomDataSource,
    private val weatherDataSource: WeatherDataSource
) : WeatherRepository {

    override suspend fun loadWeather(latitude: Double, longitude: Double): Result<Weather> {
        val loadWeather = weatherDataSource.loadWeather(latitude, longitude)
        return loadWeather
    }

    override suspend fun insertWeatherToLocalDB(weather: Weather) {
        roomDataSource.insertWeatherToLocalDB(weather)
    }

    override suspend fun fetchLastWeatherFromLocalDB(): Weather {
      return roomDataSource.fetchLastWeatherFromLocalDB()

    }

    companion object {
        private const val TAG = "WeatherRepository"
    }
}
