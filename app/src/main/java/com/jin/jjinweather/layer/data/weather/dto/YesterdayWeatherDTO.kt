package com.jin.jjinweather.layer.data.weather.dto

import com.google.gson.annotations.SerializedName

data class YesterdayWeatherDTO(
    @SerializedName("data") val data: List<YesterdayTempDTO>
)

data class YesterdayTempDTO(
    @SerializedName("temp") val temp: Double
)
