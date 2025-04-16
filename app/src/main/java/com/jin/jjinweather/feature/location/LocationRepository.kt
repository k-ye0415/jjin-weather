package com.jin.jjinweather.feature.location

import com.jin.jjinweather.layer.domain.model.location.GeoPoint

interface LocationRepository {
    suspend fun currentGeoPoint(): GeoPoint

    suspend fun findCityNameAt(location: GeoPoint): String
}
