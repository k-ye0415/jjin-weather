package com.jin.jjinweather.feature.weather.data.model.dto

import com.google.gson.annotations.SerializedName

data class HourlyWeatherDTO(
    @SerializedName("dt") val dt: Long,
    @SerializedName("temp") val temperature: Double,
    @SerializedName("weather") val weather: List<WeatherConditionDTO>
)
