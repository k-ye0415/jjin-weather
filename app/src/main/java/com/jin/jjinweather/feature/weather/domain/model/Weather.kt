package com.jin.jjinweather.feature.weather.domain.model

import androidx.annotation.DrawableRes
import com.jin.jjinweather.R
import java.time.Instant
import java.time.LocalTime
import java.util.Calendar

data class Weather(
    val dayWeather: DayWeather,
    val yesterdayWeather: TemperatureSnapshot,
    val forecast: Forecast
)

data class DayWeather(
    val date: Calendar,
    val icon: WeatherIcon,
    val temperature: Number,
    val sunrise: LocalTime,
    val sunset: LocalTime,
    val moonPhase: Double,
    val temperatureRange: TemperatureRange
)

data class TemperatureSnapshot(
    val timeStamp: Instant,
    val icon: WeatherIcon,
    val temperature: Number,
)

data class Forecast(
    val hourly: HourlyForecast,
    val daily: List<DailyForecast>
)

typealias HourlyForecast = List<TemperatureSnapshot>

data class DailyForecast(
    val date: Calendar,
    val icon: WeatherIcon,
    val temperatureRange: TemperatureRange
)

data class TemperatureRange(
    val min: Number,
    val max: Number
)

enum class WeatherIcon(@DrawableRes val drawableRes: Int, val codes: Set<String>) {
    CLEAR_SKY_DAY(R.drawable.ic_main_clear_sky_day, setOf("01d")),
    CLEAR_SKY_NIGHT(R.drawable.ic_main_clear_sky_night, setOf("01n")),
    FEW_CLOUDS_DAY(R.drawable.ic_main_few_clouds_day, setOf("02d")),
    FEW_CLOUDS_NIGHT(R.drawable.ic_main_few_clouds_night, setOf("02n")),
    SCATTERED_CLOUDS(R.drawable.ic_main_scattered_clouds, setOf("03d", "03n", "04d", "04n")),
    SHOWER_RAIN(R.drawable.ic_main_shower_rain, setOf("09d", "09n")),
    RAIN_DAY(R.drawable.ic_main_rain_day, setOf("10d")),
    RAIN_NIGHT(R.drawable.ic_main_rain_night, setOf("10n")),
    THUNDERSTORM(R.drawable.ic_main_thunderstorm, setOf("11d", "11n")),
    SNOW(R.drawable.ic_main_snow, setOf("13d", "13n")),
    MIST(R.drawable.ic_main_mist, setOf("50d", "50n"));

    companion object {
        fun findByWeatherIcon(code: String): WeatherIcon {
            return entries.firstOrNull { code in it.codes } ?: CLEAR_SKY_DAY
        }
    }
}
