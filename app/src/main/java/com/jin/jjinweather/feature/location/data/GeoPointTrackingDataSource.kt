package com.jin.jjinweather.feature.location.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jin.jjinweather.feature.location.data.model.GeoPointEntity

@Dao
interface GeoPointTrackingDataSource {
    @Insert
    suspend fun markAsLatestLocation(geoPointEntity: GeoPointEntity)

    @Query("SELECT * FROM geoPoint WHERE pageNumber = :pageNumber")
    suspend fun latestGeoPointOrNull(pageNumber: Int): GeoPointEntity?

    @Query("SELECT * FROM geoPoint")
    suspend fun allGeoPoints(): List<GeoPointEntity>
}
