package com.jin.jjinweather.layer.domain.model.weather

data class Weather(
    val cityName: String,
    val iconResId: Int,
    val currentTemperature: Double,
    val yesterdayTemperature: Double,
    val minTemperature: Double,
    val maxTemperature: Double,
    val hourlyWeatherList: List<HourlyWeather>,
    val dailyWeatherList: List<DailyWeather>,
    val sunrise: Long,
    val sunset: Long,
    val moonPhase: Int
)
