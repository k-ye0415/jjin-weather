package com.jin.jjinweather.feature.outfit.data

interface OpenAiDataSource {
    suspend fun generateImagePrompt(temperature: Int, feelsLikeTemperature: Int): Result<String>
}
