package com.jin.jjinweather.layer.domain.model.weather

data class DailyWeather(
    val forecastDay: Long,
    val iconCode: String,
    val minTemperature: Number,
    val maxTemperature: Number
)
