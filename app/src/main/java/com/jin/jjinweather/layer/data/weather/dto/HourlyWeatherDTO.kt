package com.jin.jjinweather.layer.data.weather.dto

import com.google.gson.annotations.SerializedName

data class HourlyWeatherDTO(
    @SerializedName("dt") val dt: Long,
    @SerializedName("temp") val temperature: Double,
    @SerializedName("weather") val weather: List<WeatherConditionDTO>
)
