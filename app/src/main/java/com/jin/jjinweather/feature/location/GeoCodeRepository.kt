package com.jin.jjinweather.feature.location

import com.jin.jjinweather.layer.domain.model.location.GeoPoint

interface GeoCodeRepository {
    suspend fun findCityNameAt(location: GeoPoint): String
}
