package com.jin.jjinweather.feature.weather

data class HourlyWeather(
    val forecastTime: Long,
    val iconCode: String,
    val temperature: Number
)
