package com.jin.jjinweather.feature.outfit.domain

class GetOutfitUseCase(private val outfitRepository: OutfitRepository) {
    suspend operator fun invoke(temperature: Int): Result<List<String>> {
        return outfitRepository.fetchImagesByTemperature(temperature)
    }
}
