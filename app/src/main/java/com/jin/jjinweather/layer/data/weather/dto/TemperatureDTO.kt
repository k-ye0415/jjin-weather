package com.jin.jjinweather.layer.data.weather.dto

import com.google.gson.annotations.SerializedName

data class TemperatureDTO(
    @SerializedName("min") val min: Double,
    @SerializedName("max") val max: Double,
    @SerializedName("day") val day: Double
)
