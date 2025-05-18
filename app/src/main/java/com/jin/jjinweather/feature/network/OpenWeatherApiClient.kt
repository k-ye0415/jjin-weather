package com.jin.jjinweather.feature.network

import com.jin.jjinweather.feature.weather.data.OpenWeatherApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenWeatherApiClient {
    fun createService(): OpenWeatherApi {
        val client = OkHttpClient.Builder().build()
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/3.0/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenWeatherApi::class.java)
    }
}
