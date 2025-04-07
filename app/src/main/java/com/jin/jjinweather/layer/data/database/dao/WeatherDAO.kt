package com.jin.jjinweather.layer.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.jin.jjinweather.layer.data.database.entity.WeatherEntity

@Dao
interface WeatherDAO {
    @Insert
    suspend fun insert(weatherEntity: WeatherEntity)
}
