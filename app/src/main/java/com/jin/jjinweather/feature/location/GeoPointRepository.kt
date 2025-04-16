package com.jin.jjinweather.feature.location

import com.jin.jjinweather.layer.domain.model.location.GeoPoint

interface GeoPointRepository {
    suspend fun currentGeoPoint(): GeoPoint
}
