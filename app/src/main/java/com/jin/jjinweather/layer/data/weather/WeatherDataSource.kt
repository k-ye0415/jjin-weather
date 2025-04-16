package com.jin.jjinweather.layer.data.weather

import android.util.Log
import com.jin.jjinweather.BuildConfig
import com.jin.jjinweather.feature.location.data.GeoCodeDataSource
import com.jin.jjinweather.layer.data.weather.dto.WeatherDTO
import com.jin.jjinweather.layer.domain.model.weather.DailyWeather
import com.jin.jjinweather.layer.domain.model.weather.HourlyWeather
import com.jin.jjinweather.layer.domain.model.weather.Weather
import java.time.Instant

class WeatherDataSource(
    private val weatherService: WeatherService,
    // FIXME: A data source should never dereference any other data source directly.
    private val geoCodeDataSource: GeoCodeDataSource,
) {

    suspend fun requestWeatherAt(latitude: Double, longitude: Double): Result<Weather> {
        return try {
            val cityName = geoCodeDataSource.findCityNameAt(latitude, longitude)

            val response = weatherService.queryWeather(
                latitude,
                longitude,
                "minutely",
                "metric",
                "kr",
                BuildConfig.OPEN_WEATHER_API_KEY
            )
            val yesterdayResponse = requestYesterdayWeatherAt(latitude, longitude)

            val weather = response.toWeather(cityName, yesterdayResponse)
            Result.success(weather)
        } catch (e: Exception) {
            Log.e(TAG, "loadWeather error :${e.printStackTrace()}")
            Result.failure(e)
        }
    }

    private suspend fun requestYesterdayWeatherAt(latitude: Double, longitude: Double): Double? {
        val timestamp24hAgo = Instant.now()
            .minusSeconds(60 * 60 * 24)
            .epochSecond
        return try {
            weatherService.queryYesterdayWeather(
                latitude = latitude,
                longitude = longitude,
                dateTime = timestamp24hAgo,
                "metric",
                "kr",
                BuildConfig.OPEN_WEATHER_API_KEY
            ).data.firstOrNull()?.temperature
        } catch (e: Exception) {
            Log.e(TAG, "loadYesterdayTemperature error :${e.printStackTrace()}")
            null
        }
    }

    private fun WeatherDTO.toWeather(cityName: String, yesterdayTemp: Double?): Weather {
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
            cityName = cityName,
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

    companion object {
        private const val TAG = "WeatherDataSource"
    }
}
