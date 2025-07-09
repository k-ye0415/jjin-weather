package com.jin.jjinweather.feature.outfit.data

import com.jin.jjinweather.feature.outfit.data.model.OpenAiRequest
import com.jin.jjinweather.feature.outfit.data.model.OpenAiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatGptApi {
    @POST("v1/chat/completions")
    suspend fun queryOpenAiPrompt(
        @Body body: OpenAiRequest
    ): OpenAiResponse
}
