package com.jin.jjinweather.feature.outfit.domain

interface OutfitRepository {
    suspend fun fetchImagesByTemperature(temperature: Int): Result<List<String>>
}
