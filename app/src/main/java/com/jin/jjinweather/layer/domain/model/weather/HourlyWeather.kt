package com.jin.jjinweather.layer.domain.model.weather

data class HourlyWeather(
    val forecastTime: Long,
    val iconCode: String,
    val temperature: Number
)
