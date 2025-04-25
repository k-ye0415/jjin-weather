package com.jin.jjinweather.feature.weather.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jin.jjinweather.feature.weather.domain.model.DailyWeather
import com.jin.jjinweather.feature.weather.domain.model.HourlyWeather

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun hourlyWeatherListToJsonString(value: List<HourlyWeather>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonStringToHourlyWeatherList(value: String): List<HourlyWeather> {
        val type = object : TypeToken<List<HourlyWeather>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun dailyWeatherListToJsonString(value: List<DailyWeather>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonStringToDailyWeatherList(value: String): List<DailyWeather> {
        val type = object : TypeToken<List<DailyWeather>>() {}.type
        return gson.fromJson(value, type)
    }
}
