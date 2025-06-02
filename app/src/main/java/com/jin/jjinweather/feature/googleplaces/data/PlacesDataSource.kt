package com.jin.jjinweather.feature.googleplaces.data

interface PlacesDataSource {
    suspend fun searchDistrictAt(keyword: String)
}
