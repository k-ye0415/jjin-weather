package com.jin.jjinweather.feature.weatherimpl.data

import android.util.Log
import com.jin.jjinweather.feature.weather.data.OpenWeatherDataSource
import com.jin.jjinweather.feature.weather.data.WeatherDataSource
import com.jin.jjinweather.feature.weather.data.model.dto.WeatherDTO
import com.jin.jjinweather.feature.weather.domain.model.DailyForecast
import com.jin.jjinweather.feature.weather.domain.model.DayWeather
import com.jin.jjinweather.feature.weather.domain.model.Forecast
import com.jin.jjinweather.feature.weather.domain.model.TemperatureRange
import com.jin.jjinweather.feature.weather.domain.model.TemperatureSnapshot
import com.jin.jjinweather.feature.weather.domain.model.Weather
import com.jin.jjinweather.feature.weather.domain.model.WeatherIcon
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.util.Calendar

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
            TemperatureSnapshot(
                timeStamp = Instant.ofEpochMilli(hourly.dt),
                icon = WeatherIcon.findByWeatherCode(hourly.weather.first().icon),
                temperature = hourly.temperature
            )
        }
        val dailyList = daily.map { daily ->
            DailyForecast(
                date = Calendar.getInstance().apply { timeInMillis = daily.dt },
                icon = WeatherIcon.findByWeatherCode(daily.weather.first().icon),
                temperatureRange = TemperatureRange(min = daily.temperature.min, max = daily.temperature.max)
            )
        }

        return Weather(
            dayWeather = DayWeather(
                date = Calendar.getInstance(),
                icon = WeatherIcon.findByWeatherCode(current.weather.first().icon),
                temperature = current.temperature,
                sunrise = convertLongToLocalTime(current.sunrise),
                sunset = convertLongToLocalTime(current.sunset),
                moonPhase = daily.first().moonPhase,
                temperatureRange = TemperatureRange(
                    min = daily.first().temperature.min,
                    max = daily.first().temperature.max
                ),
            ),
            yesterdayWeather = TemperatureSnapshot( // FIXME : need Yesterday timeStamp, icon
                timeStamp = Instant.now(),
                icon = WeatherIcon.RAIN_NIGHT,
                temperature = yesterdayTemp ?: current.temperature
            ),
            forecast = Forecast(hourlyList, dailyList)
        )
    }

    private fun convertLongToLocalTime(epoch: Long): LocalTime {
        return Instant.ofEpochSecond(epoch)
            .atZone(ZoneId.systemDefault())
            .toLocalTime()
    }

    private companion object {
        const val TAG = "WeatherDataSource"
    }
}
