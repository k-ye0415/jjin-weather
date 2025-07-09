package com.jin.jjinweather.feature.outfit.domain

interface OutfitRepository {
    suspend fun fetchOutfitImgTypeByTemperature(
        cityName: String,
        temperature: Int,
        feelsLikeTemperature: Int,
        weather: String
    ): Result<List<String>>
}
