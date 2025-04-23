package com.jin.jjinweather.feature.weatherimpl.data

import android.util.Log
import com.jin.jjinweather.feature.weather.data.OpenWeatherDataSource
import com.jin.jjinweather.feature.weather.data.WeatherDataSource
import com.jin.jjinweather.feature.weather.data.model.dto.WeatherDTO
import com.jin.jjinweather.feature.weather.domain.model.DailyWeather
import com.jin.jjinweather.feature.weather.domain.model.HourlyWeather
import com.jin.jjinweather.feature.weather.domain.model.Weather

class WeatherDataSourceImpl(
    private val openWeatherDataSource: OpenWeatherDataSource
) : WeatherDataSource {
    override suspend fun requestWeatherAt(latitude: Double, longitude: Double): Result<Weather> {
        return try {
            val response = openWeatherDataSource.fetchWeather(latitude, longitude)
            val yesterdayResponse = requestYesterdayWeatherAt(latitude, longitude)

            val weather = response.toWeather(yesterdayResponse)
            Result.success(weather)
        } catch (e: Exception) {
            Log.e(TAG, "requestWeather error :${e.printStackTrace()}")
            Result.failure(e)
        }
    }

    override suspend fun requestYesterdayWeatherAt(latitude: Double, longitude: Double): Double? {
        return try {
            openWeatherDataSource.fetchYesterdayWeather(latitude, longitude).data.firstOrNull()?.temperature
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
