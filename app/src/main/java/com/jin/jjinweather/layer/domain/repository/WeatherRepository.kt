package com.jin.jjinweather.layer.domain.repository

import com.jin.jjinweather.layer.domain.model.weather.Weather

interface WeatherRepository {
    suspend fun loadWeather(latitude: Double, longitude: Double): Result<Weather>
    suspend fun insertWeatherToLocalDB(weather: Weather)
    suspend fun fetchLastWeatherFromLocalDB(): Weather
}
