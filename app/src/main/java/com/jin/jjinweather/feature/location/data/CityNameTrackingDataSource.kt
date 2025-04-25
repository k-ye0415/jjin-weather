package com.jin.jjinweather.feature.location.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.jin.jjinweather.feature.location.data.model.CityNameEntity

@Dao
interface CityNameTrackingDataSource {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun markAsLatestCityName(cityNameEntity: CityNameEntity)
}
