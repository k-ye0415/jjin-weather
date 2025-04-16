package com.jin.jjinweather.feature.location.data

interface GeoCodingDataSource {
    suspend fun findCityNameAt(latitude: Double, longitude: Double): String
}
