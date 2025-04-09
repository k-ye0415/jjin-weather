package com.jin.jjinweather.layer.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jin.jjinweather.layer.data.database.entity.GeoPointEntity

@Dao
interface GeoPointDAO {
    @Insert
    suspend fun saveGeoPointToLocalDB(geoPointEntity: GeoPointEntity)

    @Query("SELECT * FROM geoPoint ORDER BY id DESC LIMIT 1")
    suspend fun loadLastGeoPointFromLocalDB(): GeoPointEntity?
}
