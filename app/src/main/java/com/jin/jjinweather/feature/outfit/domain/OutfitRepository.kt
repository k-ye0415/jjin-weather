package com.jin.jjinweather.feature.outfit.domain

interface OutfitRepository {
    suspend fun fetchOutfitImgTypeByTemperature(temperature: Int, feelsLikeTemperature: Int): Result<List<String>>
}
