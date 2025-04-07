package com.jin.jjinweather.layer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jin.jjinweather.layer.data.database.dao.WeatherDAO
import com.jin.jjinweather.layer.data.database.entity.WeatherEntity

@Database(entities = [WeatherEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDAO
}
