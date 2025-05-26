package com.jin.jjinweather.feature.network

import com.jin.jjinweather.feature.weather.data.OpenWeatherApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenWeatherApiClient {
    fun createService(): OpenWeatherApi {
        val client = OkHttpClientProvider.baseHttpClient
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/3.0/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(OkHttpClientProvider.gson))
            .build()
            .create(OpenWeatherApi::class.java)
    }
}
