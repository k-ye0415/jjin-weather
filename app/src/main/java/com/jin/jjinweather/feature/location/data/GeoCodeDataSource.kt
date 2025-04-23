package com.jin.jjinweather.feature.location.data

interface GeoCodeDataSource {
    suspend fun findCityNameAt(latitude: Double, longitude: Double): Result<String>
}
