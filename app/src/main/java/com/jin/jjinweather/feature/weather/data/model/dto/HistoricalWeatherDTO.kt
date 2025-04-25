package com.jin.jjinweather.feature.weather.data.model.dto

import com.google.gson.annotations.SerializedName

data class HistoricalWeatherDTO(
    @SerializedName("data") val data: List<HistoricalTempDTO>
)

data class HistoricalTempDTO(
    @SerializedName("temp") val temperature: Double
)
