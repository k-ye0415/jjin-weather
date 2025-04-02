package com.jin.jjinweather.layer.data.weather

import android.util.Log
import com.jin.jjinweather.BuildConfig
import com.jin.jjinweather.layer.domain.model.weather.DailyWeather
import com.jin.jjinweather.layer.domain.model.weather.HourlyWeather
import com.jin.jjinweather.layer.domain.model.weather.Weather
import java.time.Instant

class WeatherDataSource(private val weatherService: WeatherService) {

    suspend fun loadWeather(latitude: Double, longitude: Double): Weather? {
        return try {
            val response = weatherService.fetchWeather(
                latitude,
                longitude,
                "minutely",
                "metric",
                "kr",
                BuildConfig.OPEN_WEATHER_API_KEY
            )
            val yesterdayResponse = loadYesterdayTemperature(latitude, longitude)

            val hourlyList = response.hourly.map { hourly ->
                HourlyWeather(
                    forecastTime = hourly.dt,
                    iconResId = 1, // icon resource 적용 필요
                    temperature = hourly.temp
                )
            }
            val dailyList = response.daily.map { daily ->
                DailyWeather(
                    forecastDay = daily.dt,
                    iconResId = 1, // icon resource 적용 필요
                    minTemperature = daily.temp.min,
                    maxTemperature = daily.temp.max
                )
            }
            Weather(
                cityName = "", // repository 에서 적용
                iconResId = 1, // icon resource 적용 필요
                currentTemperature = response.current.temp,
                yesterdayTemperature = yesterdayResponse ?: response.current.temp,
                minTemperature = response.daily[0].temp.min,
                maxTemperature = response.daily[0].temp.max,
                hourlyWeatherList = hourlyList,
                dailyWeatherList = dailyList,
                sunrise = response.current.sunrise,
                sunset = response.current.sunrise,
                moonPhase = response.daily[0].moonPhase
            )
        } catch (e: Exception) {
            Log.e(TAG, "loadWeather error :${e.printStackTrace()}")
            null
        }
    }

    private suspend fun loadYesterdayTemperature(latitude: Double, longitude: Double): Double? {
        val timestamp24hAgo = Instant.now()
            .minusSeconds(60 * 60 * 24)
            .epochSecond
        return try {
            weatherService.fetchYesterdayTemperature(
                latitude = latitude,
                longitude = longitude,
                dateTime = timestamp24hAgo,
                "metric",
                "kr",
                BuildConfig.OPEN_WEATHER_API_KEY
            ).data.firstOrNull()?.temp
        } catch (e: Exception) {
            Log.e(TAG, "loadYesterdayTemperature error :${e.printStackTrace()}")
            null
        }
    }

    companion object {
        private const val TAG = "WeatherDataSource"
    }
}
