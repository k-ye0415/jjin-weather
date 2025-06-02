package com.jin.jjinweather.feature.googleplaces.data

import com.jin.jjinweather.feature.googleplaces.domain.PlacesRepository

class PlacesRepositoryImpl(private val placesDataSource: PlacesDataSource) : PlacesRepository {
    override suspend fun searchDistrictAt(keyword: String) {
        // do work
    }
}
