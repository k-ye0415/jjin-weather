package com.jin.jjinweather.feature.outfit.data

interface OpenAiDataSource {
    suspend fun generateOutfitImgTypes(
        cityName: String,
        temperature: Int,
        feelsLikeTemperature: Int,
        weather: String
    ): Result<String>
}
