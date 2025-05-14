package com.jin.jjinweather.feature.outfitImpl

import com.jin.jjinweather.feature.outfit.data.ChatGptApi
import com.jin.jjinweather.feature.outfit.data.OpenAiDataSource
import com.jin.jjinweather.feature.outfit.data.model.OpenAiChat
import com.jin.jjinweather.feature.outfit.data.model.OpenAiRequest

class OpenAiDataSourceImpl(
    private val chatGPTApi: ChatGptApi,
    private val gptApiKey: String,
) : OpenAiDataSource {
    override suspend fun generateImagePrompt(temperature: Int): Result<String> {
        val clothes = shuffleOutfitsByTemperature(temperature)
        val requestPrompt = buildClothingImagePrompt(temperature, clothes.first(), clothes.last())
        val openAiRequest = OpenAiRequest(
            model = "gpt-4o",
            messages = listOf(
                OpenAiChat(
                    role = "system",
                    content = "You're a stylist who describes outfits for icon generation."
                ),
                OpenAiChat(role = "user", content = requestPrompt)
            )
        )
        val openAiResponse = chatGPTApi.queryOpenAiPrompt("Bearer $gptApiKey", openAiRequest)
        val prompt = openAiResponse.choices.firstOrNull()?.message?.content
        return if (prompt.isNullOrEmpty()) {
            Result.failure(Exception())
        } else {
            Result.success(prompt)
        }
    }
}

private fun buildClothingImagePrompt(temperature: Int, firstClothes: String, secondClothes: String): String = """
I need an English object description for a very simple clothing icon.
Draw a picture so that $firstClothes and $secondClothes are kept side by side without overlapping.
The icon should simply represent the $firstClothes and $secondClothes appropriate for ${temperature}°C weather.
The English object description must have a condition that the background color must be white, contain a very simple style, and do not allow temperature.
""".trimIndent()

private fun shuffleOutfitsByTemperature(temperature: Int): List<String> {
    val outfits = when (temperature) {
        in -30..4 -> listOf("padded jacket", "heavy coat", "knit sweater", "thick pants", "hoodie")
        in 5..8 -> listOf("coat", "knit sweater", "thick pants", "hoodie")
        in 9..11 -> listOf("trench coat", "sweatshirt", "jeans", "jacket")
        in 12..16 -> listOf("cardigan", "hoodie", "cotton pants", "crewneck sweatshirt", "jeans")
        in 17..19 -> listOf("windbreaker", "light cardigan", "hoodie", "crewneck sweatshirt", "slacks", "long pants")
        in 20..22 -> listOf("light cardigan", "long-sleeved t-shirt", "shirt", "cotton pants", "slacks")
        in 23..27 -> listOf("short-sleeved t-shirt", "light shirt", "shorts", "cotton pants")
        else -> listOf("sleeveless shirt", "short-sleeved t-shirt", "shorts", "short skirt")
    }
    return outfits.shuffled()
}
