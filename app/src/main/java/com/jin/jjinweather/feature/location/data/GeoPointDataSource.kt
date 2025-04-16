package com.jin.jjinweather.feature.location.data

import com.jin.jjinweather.layer.domain.model.location.GeoPoint

interface GeoPointDataSource {
    fun currentGeoPoint(): Result<GeoPoint>
}
