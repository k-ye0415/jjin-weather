package com.jin.jjinweather.feature.outfitImpl

import com.jin.jjinweather.feature.outfit.data.ChatGptApi
import com.jin.jjinweather.feature.outfit.data.DalleDataSource
import com.jin.jjinweather.feature.outfit.data.model.DalleRequest

class DalleDataSourceImpl(
    private val chatGPTApi: ChatGptApi,
    private val gptApiKey: String,
) : DalleDataSource {
    override suspend fun requestOutfitImageGeneration(prompt: String): Result<String> {
        val dalleRequest = DalleRequest(
            model = "dall-e-3",
            prompt = prompt,
            n = 1,
            size = "1792x1024"
        )
        val response = chatGPTApi.queryImageGeneration("Bearer $gptApiKey", dalleRequest)
        val imageUrl = response.data.firstOrNull()?.url
        return if (imageUrl.isNullOrEmpty()) {
            Result.failure(Exception())
        } else {
            Result.success(imageUrl)
        }
    }
}
