package com.jin.jjinweather.feature.weatherimpl.data

import android.util.Log
import com.jin.jjinweather.feature.network.TranslateApiClient
import com.jin.jjinweather.feature.weather.data.OpenWeatherApi
import com.jin.jjinweather.feature.weather.data.WeatherDataSource
import com.jin.jjinweather.feature.weather.data.model.dto.WeatherDto
import com.jin.jjinweather.feature.weather.domain.model.DailyForecast
import com.jin.jjinweather.feature.weather.domain.model.DayWeather
import com.jin.jjinweather.feature.weather.domain.model.FeelsLikeTemperature
import com.jin.jjinweather.feature.weather.domain.model.Forecast
import com.jin.jjinweather.feature.weather.domain.model.MoonPhaseType
import com.jin.jjinweather.feature.weather.domain.model.SunCycle
import com.jin.jjinweather.feature.weather.domain.model.TemperatureRange
import com.jin.jjinweather.feature.weather.domain.model.TemperatureSnapshot
import com.jin.jjinweather.feature.weather.domain.model.TemperatureSnapshotWithRain
import com.jin.jjinweather.feature.weather.domain.model.Weather
import com.jin.jjinweather.feature.weather.domain.model.WeatherIcon
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId
import java.util.Calendar
import java.util.GregorianCalendar

class WeatherDataSourceImpl(
    private val openWeatherApi: OpenWeatherApi,
    private val apiKey: String,
) : WeatherDataSource {

    override suspend fun requestWeatherAt(pageNumber: Int, latitude: Double, longitude: Double): Result<Weather> {
        return try {
            val response = openWeatherApi.queryWeather(
                latitude = latitude,
                longitude = longitude,
                exclude = EXCLUDE,
                units = UNITS,
                lang = LANGUAGE,
                apiKey = apiKey
            )
            val yesterdayResponse = requestYesterdayWeatherAt(latitude, longitude)
            val weather = response.toWeather(pageNumber, yesterdayResponse)
            Result.success(weather)
        } catch (e: Exception) {
            Log.e(TAG, "requestWeather error :${e.printStackTrace()}")
            Result.failure(e)
        }
    }

    override suspend fun requestYesterdayWeatherAt(latitude: Double, longitude: Double): Double? {
        return try {
            val timestamp24hAgo = Instant.now()
                .minusSeconds(60 * 60 * 24)
                .epochSecond
            openWeatherApi.queryHistoricalWeather(
                latitude = latitude,
                longitude = longitude,
                dateTime = timestamp24hAgo,
                units = UNITS,
                lang = LANGUAGE,
                apiKey = apiKey
            ).data.firstOrNull()?.temperature
        } catch (e: Exception) {
            Log.e(TAG, "requestYesterdayWeather error :${e.printStackTrace()}")
            null
        }
    }

    private suspend fun WeatherDto.toWeather(pageNumber: Int, yesterdayTemp: Double?): Weather {
        val hourlyList = hourly.map { hourly ->
            TemperatureSnapshotWithRain(
                timeStamp = Instant.ofEpochSecond(hourly.dt),
                icon = WeatherIcon.findByWeatherCode(hourly.weather.firstOrNull()?.icon.orEmpty()),
                temperature = hourly.temperature,
                rainProbability = hourly.rainProbability,
                precipitation = hourly.precipitation?.precipitation ?: 0.0,
            )
        }
        val dailyList = daily.map { daily ->
            DailyForecast(
                date = Calendar.getInstance().apply { timeInMillis = daily.dt * 1000 },
                icon = WeatherIcon.findByWeatherCode(daily.weather.firstOrNull()?.icon.orEmpty()),
                temperatureRange = TemperatureRange(min = daily.temperature.min, max = daily.temperature.max),
                sunCycle = SunCycle(
                    epochTimestampToLocalTime(daily.sunrise, timeZone),
                    epochTimestampToLocalTime(daily.sunset, timeZone)
                ),
                feelsLikeTemperature = FeelsLikeTemperature(
                    daily.feelsLikeTemperatureRange.day,
                    daily.feelsLikeTemperatureRange.night
                ),
                summary = TranslateApiClient.translateText(daily.summary),
                rainProbability = daily.rainProbability,
                precipitation = daily.precipitation ?: 0.0,
            )
        }

        return Weather(
            pageNumber = pageNumber,
            timeZone = timeZone,
            dayWeather = DayWeather(
                date = convertUnixToCalendar(current.dateTime, timeZone),
                icon = WeatherIcon.findByWeatherCode(current.weather.firstOrNull()?.icon.orEmpty()),
                temperature = current.temperature,
                description = current.weather.firstOrNull()?.description.orEmpty(),
                sunCycle = SunCycle(
                    epochTimestampToLocalTime(current.sunrise, timeZone),
                    epochTimestampToLocalTime(current.sunset, timeZone)
                ),
                moonPhase = MoonPhaseType.findByMoonPhase(daily.firstOrNull()?.moonPhase ?: DEFAULT_MOON_PHASE),
                feelsLikeTemperature = current.feelsLikeTemperature,
                temperatureRange = TemperatureRange(
                    min = daily.firstOrNull()?.temperature?.min ?: DEFAULT_MIN_TEMPERATURE,
                    max = daily.firstOrNull()?.temperature?.max ?: DEFAULT_MAX_TEMPERATURE
                ),
                rain = current.precipitation?.precipitation ?: 0.0
            ),
            yesterdayWeather = TemperatureSnapshot( // FIXME : need Yesterday timeStamp, icon
                timeStamp = Instant.now(),
                icon = WeatherIcon.RAIN_NIGHT,
                temperature = yesterdayTemp ?: current.temperature
            ),
            forecast = Forecast(hourlyList, dailyList)
        )
    }

    private fun convertUnixToCalendar(seconds: Long, timezoneId: String): Calendar {
        val zoneId = ZoneId.of(timezoneId)
        val instant = Instant.ofEpochSecond(seconds)
        val zonedDateTime = instant.atZone(zoneId)
        return GregorianCalendar.from(zonedDateTime)
    }

    private fun epochTimestampToLocalTime(epoch: Long, timezoneId: String): LocalTime {
        return Instant.ofEpochSecond(epoch)
            .atZone(ZoneId.of(timezoneId))
            .toLocalTime()
    }

    private companion object {
        const val TAG = "WeatherDataSource"
        const val DEFAULT_MOON_PHASE = 0.0
        const val DEFAULT_MIN_TEMPERATURE = 0
        const val DEFAULT_MAX_TEMPERATURE = 30
        const val EXCLUDE = "minutely"
        const val UNITS = "metric"
        const val LANGUAGE = "kr"
    }
}
