package com.jin.jjinweather.feature.weather.data.model.dto

import com.google.gson.annotations.SerializedName

data class RainDto(
    @SerializedName("1h") val precipitation: Double
)
