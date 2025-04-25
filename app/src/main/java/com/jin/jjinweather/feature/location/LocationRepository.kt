package com.jin.jjinweather.feature.location

interface LocationRepository {
    suspend fun currentGeoPoint(): GeoPoint

    suspend fun findCityNameAt(location: GeoPoint): String
}
