package com.jin.jjinweather.feature.weather.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jin.jjinweather.feature.weather.domain.model.DailyForecast
import com.jin.jjinweather.feature.weather.domain.model.HourlyForecast

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun hourlyWeatherListToJsonString(value: HourlyForecast): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonStringToHourlyWeatherList(value: String): HourlyForecast {
        val type = object : TypeToken<HourlyForecast>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun dailyWeatherListToJsonString(value: List<DailyForecast>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonStringToDailyWeatherList(value: String): List<DailyForecast> {
        val type = object : TypeToken<List<DailyForecast>>() {}.type
        return gson.fromJson(value, type)
    }
}
