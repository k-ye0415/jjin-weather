package com.jin.jjinweather.feature.weather.data.model.dto

import com.google.gson.annotations.SerializedName

data class WeatherDTO(
    @SerializedName("current") val current: CurrentWeatherDTO,
    @SerializedName("hourly") val hourly: List<HourlyWeatherDTO>,
    @SerializedName("daily") val daily: List<DailyWeatherDTO>
)
