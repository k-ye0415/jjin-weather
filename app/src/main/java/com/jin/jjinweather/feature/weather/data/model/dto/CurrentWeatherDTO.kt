package com.jin.jjinweather.feature.weather.data.model.dto

import com.google.gson.annotations.SerializedName

data class CurrentWeatherDTO(
    @SerializedName("temp") val temperature: Double,
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long,
    @SerializedName("weather") val weather: List<WeatherConditionDTO>
)
