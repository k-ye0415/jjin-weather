package com.jin.jjinweather.layer.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jin.jjinweather.layer.data.database.entity.WeatherEntity

@Dao
interface WeatherDAO {
    @Insert
    suspend fun insert(weatherEntity: WeatherEntity)

    @Query("SELECT * FROM weather ORDER BY id DESC LIMIT 1")
    suspend fun fetchLastWeather(): WeatherEntity
}
