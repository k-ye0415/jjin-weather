package com.jin.jjinweather.feature.outfit.data.model

data class OpenAiChat(
    val role: String,
    val content: String
)

data class OpenAiRequest(
    val model: String, // 또는 "gpt-3.5-turbo"
    val messages: List<OpenAiChat>
)

data class OpenAiResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: OpenAiChat
)
