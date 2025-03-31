package com.jin.jjinweather.layer.domain.model.weather

data class HourlyWeather(
    val forecastTime: Long,
    val iconResId: Number,
    val temperature: Number
)
