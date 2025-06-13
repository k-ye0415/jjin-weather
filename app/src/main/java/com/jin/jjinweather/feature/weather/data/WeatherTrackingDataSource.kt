package com.jin.jjinweather.feature.weather.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jin.jjinweather.feature.weather.data.model.WeatherEntity

@Dao
interface WeatherTrackingDataSource {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun markAsLatestWeather(weatherEntity: WeatherEntity)

    @Query("SELECT * FROM weather WHERE pageNumber = :pageNumber")
    suspend fun latestWeatherOrNull(pageNumber: Int): WeatherEntity?
}
