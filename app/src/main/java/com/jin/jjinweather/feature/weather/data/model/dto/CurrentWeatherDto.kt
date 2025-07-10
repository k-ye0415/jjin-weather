package com.jin.jjinweather.feature.weather.data.model.dto

import com.google.gson.annotations.SerializedName

data class CurrentWeatherDto(
    @SerializedName("dt") val dateTime: Long,
    @SerializedName("temp") val temperature: Double,
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long,
    @SerializedName("feels_like") val feelsLikeTemperature: Double,
    @SerializedName("weather") val weather: List<WeatherConditionDto>,
    @SerializedName("rain") val precipitation: RainDto?
)
