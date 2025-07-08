package com.jin.jjinweather.feature.outfitImpl

import com.jin.jjinweather.feature.outfit.data.ChatGptApi
import com.jin.jjinweather.feature.outfit.data.OpenAiDataSource
import com.jin.jjinweather.feature.outfit.data.model.OpenAiChat
import com.jin.jjinweather.feature.outfit.data.model.OpenAiRequest

class OpenAiDataSourceImpl(
    private val chatGPTApi: ChatGptApi
) : OpenAiDataSource {

    override suspend fun generateOutfitImgTypes(temperature: Int, feelsLikeTemperature: Int): Result<String> {
        // FIXME 위치, 날씨
        val requestPrompt = "Daejeon, ${temperature}°C, feels like ${feelsLikeTemperature}°C, cloudy"
        val openAiRequest = OpenAiRequest(
            model = MODEL,
            messages = listOf(
                OpenAiChat(
                    role = ROLE_SYSTEM,
                    content = CONTENT
                ),
                OpenAiChat(role = ROLE_USER, content = requestPrompt)
            )
        )
        val openAiResponse = chatGPTApi.queryOpenAiPrompt(openAiRequest)
        val response = openAiResponse.choices.firstOrNull()?.message?.content
        return if (response.isNullOrEmpty()) {
            Result.failure(Exception())
        } else {
            Result.success(response)
        }
    }

    private companion object {
        const val MODEL = "gpt-4o"
        const val ROLE_SYSTEM = "system"
        const val ROLE_USER = "user"
        const val CONTENT =
            "You are a fashion recommendation expert who suggests appropriate clothing based on the user's weather and location data. The user will provide the location, temperature, feels-like temperature, and weather condition (e.g., cloudy, rainy, snowy). You must respond with up to two clothing suggestions, each consisting of a single word, separated by a comma. Do not include any sentences or explanations. Only choose from the following list of words: Puffer, Thermal Clothes, Scarf, Coat, Cardigan, Knit, Hoodie, Jacket, Jeans, Shirt, LongSleeve, CottonPants, TShirt, Slacks, Sleeveless, Shorts. For example, reply with 'Coat, Knit'."
    }
}
