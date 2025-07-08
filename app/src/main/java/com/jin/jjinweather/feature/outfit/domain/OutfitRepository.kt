package com.jin.jjinweather.feature.outfit.domain

interface OutfitRepository {
    suspend fun fetchImagesByTemperature(temperature: Int, feelsLikeTemperature: Int): Result<List<String>>
}
