package com.jin.jjinweather.layer.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jin.jjinweather.layer.domain.model.weather.DailyWeather
import com.jin.jjinweather.layer.domain.model.weather.HourlyWeather

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromHourlyList(value: List<HourlyWeather>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toHourlyList(value: String): List<HourlyWeather> {
        val type = object : TypeToken<List<HourlyWeather>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromDailyList(value: List<DailyWeather>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toDailyList(value: String): List<DailyWeather> {
        val type = object : TypeToken<List<DailyWeather>>() {}.type
        return gson.fromJson(value, type)
    }
}
