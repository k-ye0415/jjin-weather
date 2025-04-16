package com.jin.jjinweather.feature.location.data

import com.jin.jjinweather.feature.location.GeoCodeRepository
import com.jin.jjinweather.layer.domain.model.location.GeoPoint

class GeoCodeRepositoryImpl(
    private val geoCodeDataSource: GeoCodeDataSource
) : GeoCodeRepository {
    override suspend fun findCityNameAt(location: GeoPoint): String =
        geoCodeDataSource.findCityNameAt(location.latitude, location.longitude)
}
