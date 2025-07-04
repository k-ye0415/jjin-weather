package com.jin.jjinweather.feature.location.data

import com.jin.jjinweather.feature.location.GeoPoint

interface GeoPointDataSource {
    fun currentGeoPoint(pageNumber: Int): Result<GeoPoint>
}
