package com.jin.jjinweather.feature.weatherimpl.data

import android.util.Log
import com.jin.jjinweather.BuildConfig
import com.jin.jjinweather.feature.weather.data.OpenWeatherDataSource
import com.jin.jjinweather.feature.weather.data.WeatherDataSource
import com.jin.jjinweather.feature.weather.data.model.dto.WeatherDTO
import com.jin.jjinweather.feature.weather.DailyWeather
import com.jin.jjinweather.feature.weather.HourlyWeather
import com.jin.jjinweather.feature.weather.Weather
import java.time.Instant

class WeatherDataSourceImpl(
    private val openWeatherDataSource: OpenWeatherDataSource
) : WeatherDataSource {
    override suspend fun requestWeatherAt(latitude: Double, longitude: Double): Result<Weather> {
        return try {
            val response = openWeatherDataSource.fetchWeather(
                latitude,
                longitude,
                "minutely",
                "metric",
                "kr",
                BuildConfig.OPEN_WEATHER_API_KEY
            )
            val yesterdayResponse = requestYesterdayWeatherAt(latitude, longitude)

            val weather = response.toWeather(yesterdayResponse)
            Result.success(weather)
        } catch (e: Exception) {
            Log.e(TAG, "requestWeather error :${e.printStackTrace()}")
            Result.failure(e)
        }
    }

    override suspend fun requestYesterdayWeatherAt(latitude: Double, longitude: Double): Double? {
        val timestamp24hAgo = Instant.now()
            .minusSeconds(60 * 60 * 24)
            .epochSecond
        return try {
            openWeatherDataSource.fetchYesterdayWeather(
                latitude = latitude,
                longitude = longitude,
                dateTime = timestamp24hAgo,
                "metric",
                "kr",
                BuildConfig.OPEN_WEATHER_API_KEY
            ).data.firstOrNull()?.temperature
        } catch (e: Exception) {
            Log.e(TAG, "requestYesterdayWeather error :${e.printStackTrace()}")
            null
        }
    }

    private fun WeatherDTO.toWeather(yesterdayTemp: Double?): Weather {
        val hourlyList = hourly.map { hourly ->
            HourlyWeather(
                forecastTime = hourly.dt,
                iconCode = hourly.weather.firstOrNull()?.icon.orEmpty(),
                temperature = hourly.temperature
            )
        }

        val dailyList = daily.map { daily ->
            DailyWeather(
                forecastDay = daily.dt,
                iconCode = daily.weather.firstOrNull()?.icon.orEmpty(),
                minTemperature = daily.temperature.min,
                maxTemperature = daily.temperature.max
            )
        }

        return Weather(
            cityName = "",
            iconCode = current.weather.firstOrNull()?.icon.orEmpty(),
            currentTemperature = current.temperature,
            yesterdayTemperature = yesterdayTemp ?: current.temperature,
            minTemperature = daily.first().temperature.min,
            maxTemperature = daily.first().temperature.max,
            hourlyWeatherList = hourlyList,
            dailyWeatherList = dailyList,
            sunrise = current.sunrise,
            sunset = current.sunset,
            moonPhase = daily.first().moonPhase
        )
    }

    private companion object {
        const val TAG = "WeatherDataSource"
    }
}
