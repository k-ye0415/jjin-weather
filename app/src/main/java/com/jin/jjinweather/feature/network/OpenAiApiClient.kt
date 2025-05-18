package com.jin.jjinweather.feature.network

import com.jin.jjinweather.feature.outfit.data.ChatGptApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object OpenAiApiClient {
    fun createService(apiKey: String): ChatGptApi {
        val authInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val authorizedRequest = originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $apiKey")
                .build()
            chain.proceed(authorizedRequest)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.openai.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChatGptApi::class.java)
    }
}
