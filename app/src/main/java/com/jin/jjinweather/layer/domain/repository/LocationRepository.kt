package com.jin.jjinweather.layer.domain.repository

import com.jin.jjinweather.layer.data.database.entity.GeoPointEntity
import com.jin.jjinweather.layer.domain.model.location.GeoPoint

interface LocationRepository {
    suspend fun loadCurrentGeoPoint(): Result<GeoPoint>
    suspend fun insertGeoPoint(latitude: Double, longitude: Double)
    suspend fun fetchLastGeoPoint(): GeoPointEntity
}
