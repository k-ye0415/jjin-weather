package com.jin.jjinweather.layer.data.weather.dto

import com.google.gson.annotations.SerializedName

data class WeatherConditionDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)
