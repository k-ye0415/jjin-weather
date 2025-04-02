package com.jin.jjinweather.layer.data.weather

import com.jin.jjinweather.layer.data.RetrofitClient
import com.jin.jjinweather.layer.data.weather.dto.WeatherDTO
import com.jin.jjinweather.layer.data.weather.dto.YesterdayWeatherDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("onecall")
    suspend fun fetchWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("exclude") exclude: String,
        @Query("units") units: String,
        @Query("lang") lang: String,
        @Query("appid") apiKey: String
    ): WeatherDTO

    @GET("onecall/timemachine")
    suspend fun fetchYesterdayTemperature(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("dt") dateTime: Long,
        @Query("units") units: String,
        @Query("lang") lang: String,
        @Query("appid") apiKey: String
    ): YesterdayWeatherDTO

    companion object {
        val openWeatherApi: WeatherService =
            RetrofitClient.createService("https://api.openweathermap.org/data/3.0/")
    }
}
