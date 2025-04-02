package com.jin.jjinweather.layer.data.weather

import android.util.Log
import com.jin.jjinweather.BuildConfig
import com.jin.jjinweather.R
import com.jin.jjinweather.layer.data.location.LocationProvider
import com.jin.jjinweather.layer.data.weather.dto.WeatherDTO
import com.jin.jjinweather.layer.domain.model.weather.DailyWeather
import com.jin.jjinweather.layer.domain.model.weather.HourlyWeather
import com.jin.jjinweather.layer.domain.model.weather.Weather
import java.time.Instant

class WeatherDataSource(private val weatherService: WeatherService, private val locationProvider: LocationProvider) {

    suspend fun loadWeather(latitude: Double, longitude: Double): Weather? {
        return try {
            val cityName = locationProvider.loadCurrentCityName(latitude, longitude)

            val response = weatherService.fetchWeather(
                latitude,
                longitude,
                "minutely",
                "metric",
                "kr",
                BuildConfig.OPEN_WEATHER_API_KEY
            )

            val yesterdayResponse = loadYesterdayTemperature(latitude, longitude)

            response.toWeather(cityName, yesterdayResponse)
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

    private fun WeatherDTO.toWeather(cityName:String, yesterdayTemp: Double?): Weather {
        val hourlyList = hourly.map { hourly ->
            HourlyWeather(
                forecastTime = hourly.dt,
                iconResId = mapWeatherIconToDrawable(hourly.weather.firstOrNull()?.icon ?: ""),
                temperature = hourly.temp
            )
        }

        val dailyList = daily.map { daily ->
            DailyWeather(
                forecastDay = daily.dt,
                iconResId = mapWeatherIconToDrawable(daily.weather.firstOrNull()?.icon ?: ""),
                minTemperature = daily.temp.min,
                maxTemperature = daily.temp.max
            )
        }

        return Weather(
            cityName = cityName,
            iconResId = mapWeatherIconToDrawable(current.weather.firstOrNull()?.icon ?: ""),
            currentTemperature = current.temp,
            yesterdayTemperature = yesterdayTemp ?: current.temp,
            minTemperature = daily.first().temp.min,
            maxTemperature = daily.first().temp.max,
            hourlyWeatherList = hourlyList,
            dailyWeatherList = dailyList,
            sunrise = current.sunrise,
            sunset = current.sunset,
            moonPhase = daily.first().moonPhase
        )
    }

    private fun mapWeatherIconToDrawable(icon: String): Int {
        return when (icon) {
            "01d" -> R.drawable.ic_main_clear_sky_day
            "01n" -> R.drawable.ic_main_clear_sky_night
            "02d" -> R.drawable.ic_main_few_clouds_day
            "02n" -> R.drawable.ic_main_few_clouds_night
            "03d", "03n", "04d", "04n" -> R.drawable.ic_main_scattered_clouds
            "09d", "09n" -> R.drawable.ic_main_shower_rain
            "10d" -> R.drawable.ic_main_rain_day
            "10n" -> R.drawable.ic_main_rain_night
            "11d", "11n" -> R.drawable.ic_main_thunderstorm
            "13d", "13n" -> R.drawable.ic_main_snow
            "50d", "50n" -> R.drawable.ic_main_mist
            else -> R.drawable.ic_main_clear_sky_day
        }
    }

    companion object {
        private const val TAG = "WeatherDataSource"
    }
}
