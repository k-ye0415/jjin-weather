package com.jin.jjinweather.layer.data.weather.dto

import com.google.gson.annotations.SerializedName

data class CurrentWeatherDTO(
    @SerializedName("temp") val temp: Double,
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long,
    @SerializedName("weather") val weather: List<WeatherConditionDTO>
)
