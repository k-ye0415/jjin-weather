package com.jin.jjinweather.feature.location.data

import com.jin.jjinweather.layer.domain.model.location.GeoPoint

interface LocationProvider {
    fun currentGeoPoint(): Result<GeoPoint>
}
