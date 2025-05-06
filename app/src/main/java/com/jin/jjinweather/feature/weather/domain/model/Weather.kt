package com.jin.jjinweather.feature.weather.domain.model

import androidx.annotation.DrawableRes
import com.jin.jjinweather.R
import java.time.Instant
import java.time.LocalTime
import java.util.Calendar

/**
 * @property yesterdayTemperature API 경로 분리
 * @property minTemperature daily 에서 첫번째 인덱스 값으로 적용
 * @property maxTemperature daily 에서 첫번째 인덱스 값으로 적용
 * @property iconCode d 와 n는 day 와 night.
 * - 01d or 01n -> clear sky
 * - 02d or 02n -> few clouds
 * - 03d or 03n -> scattered clouds
 * - 04d or 04n -> broken clouds
 * - 09d or 09n -> shower rain
 * - 10d or 10n -> rain
 * - 11d or 11n -> thunderstorm
 * - 13d or 13n -> snow
 * - 50d or 50n -> mist
 * @property moonPhase 값의 범위는 0.0부터 1.0 까지 이며, 주요 값은 다음과 같다.
 * - daily 에서 첫번째 인덱스 값으로 적용
 * - 0.0 또는 1.0 -> New Moon(삭, 달이 태양과 일직선, 보이지 않음.)
 * - 0.25 -> First Quarter (상현달, 오늘쪽 반달)
 * - 0.5 -> Full Moon (보름달, 완전한 둥근달)
 * - 0.75 -> Last Quarter (하현달, 왼쪽 반달)
 */
data class Weather(
    val iconCode: String,
    val currentTemperature: Number,
    val yesterdayTemperature: Number,
    val minTemperature: Number,
    val maxTemperature: Number,
    val hourlyWeatherList: List<HourlyWeather>,
    val dailyWeatherList: List<DailyWeather>,
    val sunrise: Long,
    val sunset: Long,
    val moonPhase: Double
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
