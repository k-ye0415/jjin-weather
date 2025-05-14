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
    val description: String,
    val sunrise: LocalTime,
    val sunset: LocalTime,
    val feelsLikeTemperature: Number,
    val moonPhase: MoonPhaseType,
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
    val temperatureRange: TemperatureRange,
    val feelsLikeTemperatureRange: FeelsLikeTemperatureRange
)

data class TemperatureRange(
    val min: Number,
    val max: Number
)

data class FeelsLikeTemperatureRange(
    val dayTemperature: Number,
    val nightTemperature: Number
)


enum class WeatherIcon(@DrawableRes val drawableRes: Int) {
    CLEAR_SKY_DAY(R.drawable.ic_main_clear_sky_day),
    CLEAR_SKY_NIGHT(R.drawable.ic_main_clear_sky_night),
    FEW_CLOUDS_DAY(R.drawable.ic_main_few_clouds_day),
    FEW_CLOUDS_NIGHT(R.drawable.ic_main_few_clouds_night),
    SCATTERED_CLOUDS(R.drawable.ic_main_scattered_clouds),
    SHOWER_RAIN(R.drawable.ic_main_shower_rain),
    RAIN_DAY(R.drawable.ic_main_rain_day),
    RAIN_NIGHT(R.drawable.ic_main_rain_night),
    THUNDERSTORM(R.drawable.ic_main_thunderstorm),
    SNOW(R.drawable.ic_main_snow),
    MIST(R.drawable.ic_main_mist);

    companion object {
        fun findByWeatherCode(code: String): WeatherIcon {
            return when (code) {
                "01d" -> CLEAR_SKY_DAY
                "01n" -> CLEAR_SKY_NIGHT
                "02d" -> FEW_CLOUDS_DAY
                "02n" -> FEW_CLOUDS_NIGHT
                "03d", "03n", "04d", "04n" -> SCATTERED_CLOUDS
                "09d", "09n" -> SHOWER_RAIN
                "10d" -> RAIN_DAY
                "10n" -> RAIN_NIGHT
                "11d", "11n" -> THUNDERSTORM
                "13d", "13n" -> SNOW
                "50d", "50n" -> MIST
                else -> CLEAR_SKY_DAY
            }
        }
    }
}

enum class MoonPhaseType(@DrawableRes val drawableRes: Int, val moonName:String) {
    NEW_MOON(R.drawable.ic_mew_moon, "새로운 달"),
    START_CRESCENT_MOON(R.drawable.ic_start_crescent_moon, "초승달"),
    START_HALF_MOON(R.drawable.ic_start_half_moon, "반달"),
    BEFORE_FULL_MOON(R.drawable.ic_before_full_moon, "보름달 전"),
    FULL_MOON(R.drawable.ic_full_moon, "보름달"),
    AFTER_FULL_MOON(R.drawable.ic_after_full_moon,"보름달 후"),
    END_HALF_MOON(R.drawable.ic_end_half_moon,"반달"),
    END_CRESCENT_MOON(R.drawable.ic_end_crescent_moon,"초승달");

    companion object {
        fun findByMoonPhase(phase: Double): MoonPhaseType {
            return when {
                phase <= 0.03 || phase == 1.0 -> NEW_MOON
                phase in 0.04..0.24 -> START_CRESCENT_MOON
                phase == 0.25 -> START_HALF_MOON
                phase in 0.26..0.49 -> BEFORE_FULL_MOON
                phase == 0.5 -> FULL_MOON
                phase in 0.51..0.74 -> AFTER_FULL_MOON
                phase == 0.75 -> END_HALF_MOON
                phase in 0.76..0.99 -> END_CRESCENT_MOON
                else -> NEW_MOON
            }
        }
    }
}
