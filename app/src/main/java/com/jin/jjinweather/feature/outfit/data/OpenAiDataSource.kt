package com.jin.jjinweather.feature.outfit.data

interface OpenAiDataSource {
    suspend fun generateOutfitImgTypes(temperature: Int, feelsLikeTemperature: Int): Result<String>
}
