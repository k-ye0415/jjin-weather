package com.jin.jjinweather.feature.location.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jin.jjinweather.feature.location.data.model.CityNameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CityNameTrackingDataSource {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun markAsLatestCityName(cityNameEntity: CityNameEntity)

    @Query("SELECT * FROM cityName")
    fun observeCityNames(): Flow<List<CityNameEntity>>

    @Query("SELECT * FROM cityName WHERE pageNumber = :pageNumber")
    suspend fun queryCityName(pageNumber: Int): CityNameEntity?
}
