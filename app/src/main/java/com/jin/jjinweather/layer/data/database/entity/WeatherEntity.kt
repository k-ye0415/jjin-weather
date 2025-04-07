package com.jin.jjinweather.layer.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val latitude: Double,
    val longitude: Double,
    val cityName: String,
    val iconCode: String,
    val currentTemperature: Double,
    val yesterdayTemperature: Double,
    val minTemperature: Double,
    val maxTemperature: Double,
    val hourlyWeatherList: String,
    val dailyWeatherList: String,
    val sunrise: Long,
    val sunset: Long,
    val moonPhase: Double
)
