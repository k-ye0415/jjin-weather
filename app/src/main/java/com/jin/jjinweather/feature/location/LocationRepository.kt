package com.jin.jjinweather.feature.location

import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun currentGeoPoint(): GeoPoint
    suspend fun findCityNameAt(location: GeoPoint): String
    suspend fun insertGeoPoint(geoPoint: GeoPoint)
    suspend fun insertCityName(city: City)
    fun fetchGeoPoints(): Flow<List<GeoPoint>>
    fun fetchCityNames(): Flow<List<City>>
}
