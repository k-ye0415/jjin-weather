package com.jin.jjinweather.feature.outfit.data

import com.jin.jjinweather.feature.outfit.domain.OutfitRepository

class OutfitRepositoryImpl(private val openAiDataSource: OpenAiDataSource) : OutfitRepository {
    override suspend fun fetchOutfitImgTypeByTemperature(
        temperature: Int,
        feelsLikeTemperature: Int
    ): Result<List<String>> {
        return openAiDataSource.generateOutfitImgTypes(temperature, feelsLikeTemperature).fold(
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
