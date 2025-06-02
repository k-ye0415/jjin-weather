package com.jin.jjinweather.feature.googleplaces.domain

interface PlacesRepository {
    suspend fun searchDistrictAt(keyword: String)
}
