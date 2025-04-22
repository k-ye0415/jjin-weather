package com.jin.jjinweather.feature.weather.data.model.dto

import com.google.gson.annotations.SerializedName

data class YesterdayWeatherDTO(
    @SerializedName("data") val data: List<YesterdayTempDTO>
)

data class YesterdayTempDTO(
    @SerializedName("temp") val temperature: Double
)
