package com.jin.jjinweather.feature.weather.data.model.dto

import com.google.gson.annotations.SerializedName

data class FeelsLikeTemperatureRangeDTO(
    @SerializedName("day") val day: Double,
    @SerializedName("night") val night: Double
)
