package com.jin.jjinweather.layer.domain.model.weather

/**
 * Weather
 * property @moonPhase 설명
 * 0.0 또는 1.0 -> New Moon(삭, 달이 태양과 일직선, 보이지 않음.)
 * 0.25 -> First Quarter (상현달, 오늘쪽 반달)
 * 0.5 -> Full Moon (보름달, 완전한 둥근달)
 * 0.75 -> Last Quarter (하현달, 왼쪽 반달)
 */
data class Weather(
    val cityName: String,
    val iconResId: Int,
    val currentTemperature: Number,
    val yesterdayTemperature: Number,
    val minTemperature: Number,
    val maxTemperature: Number,
    val hourlyWeatherList: List<HourlyWeather>,
    val dailyWeatherList: List<DailyWeather>,
    val sunrise: Long,
    val sunset: Long,
    val moonPhase: Double
)
