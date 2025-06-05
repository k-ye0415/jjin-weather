package com.jin.jjinweather.feature.googleplaces.domain.model

import com.jin.jjinweather.feature.location.GeoPoint

data class District(
    val address: String,
    val geoPoint: GeoPoint
)
