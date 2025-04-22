package com.jin.jjinweather.layer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jin.jjinweather.feature.location.data.GeoPointTrackingDataSource
import com.jin.jjinweather.feature.weather.data.WeatherTrackingDataSource
import com.jin.jjinweather.feature.location.data.model.GeoPointEntity
import com.jin.jjinweather.feature.weather.data.Converters
import com.jin.jjinweather.feature.weather.data.model.WeatherEntity

@Database(entities = [WeatherEntity::class, GeoPointEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherTrackingDataSource(): WeatherTrackingDataSource
    abstract fun geoPointTrackingDataSource(): GeoPointTrackingDataSource
}
