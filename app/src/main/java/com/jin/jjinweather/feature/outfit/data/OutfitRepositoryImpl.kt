package com.jin.jjinweather.feature.outfit.data

import com.jin.jjinweather.feature.outfit.domain.OutfitRepository

class OutfitRepositoryImpl(private val openAiDataSource: OpenAiDataSource) : OutfitRepository {
    override suspend fun fetchOutfitImgTypeByTemperature(
        cityName: String,
        temperature: Int,
        feelsLikeTemperature: Int,
        weather: String
    ): Result<List<String>> {
        return openAiDataSource.generateOutfitImgTypes(cityName, temperature, feelsLikeTemperature, weather).fold(
            onSuccess = { clothesNames ->
                val result = clothesNames.split(",").map { it.trim() }
                Result.success(result)
            },
            onFailure = { Result.failure(it) }
        )
    }

    private companion object {
        val TAG = "OutfitRepository"
    }
}
