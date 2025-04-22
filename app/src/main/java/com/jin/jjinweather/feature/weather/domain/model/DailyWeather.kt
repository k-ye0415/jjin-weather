package com.jin.jjinweather.feature.weather.domain.model

data class DailyWeather(
    val forecastDay: Long,
    val iconCode: String,
    val minTemperature: Number,
    val maxTemperature: Number
)
