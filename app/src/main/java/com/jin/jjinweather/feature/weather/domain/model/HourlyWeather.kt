package com.jin.jjinweather.feature.weather.domain.model

data class HourlyWeather(
    val forecastTime: Long,
    val iconCode: String,
    val temperature: Number
)
