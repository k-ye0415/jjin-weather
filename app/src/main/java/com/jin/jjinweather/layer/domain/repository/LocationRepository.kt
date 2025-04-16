package com.jin.jjinweather.layer.domain.repository

import com.jin.jjinweather.layer.domain.model.location.GeoPoint

interface LocationRepository {
    suspend fun currentGeoPoint(): GeoPoint
}
