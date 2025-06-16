package com.jin.jjinweather.feature.weather.domain.model

data class CityWeather(
    val pageNumber: Int,
    val cityName: String,
    val weather: Weather
)
