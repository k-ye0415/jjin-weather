package com.jin.jjinweather.feature.weather.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jin.jjinweather.layer.data.database.entity.WeatherEntity

@Dao
interface WeatherTrackingDataSource {
    @Insert
    suspend fun markAsLatestWeather(weatherEntity: WeatherEntity)

    @Query("SELECT * FROM weather ORDER BY id DESC LIMIT 1")
    suspend fun latestWeatherOrNull(): WeatherEntity?
}
