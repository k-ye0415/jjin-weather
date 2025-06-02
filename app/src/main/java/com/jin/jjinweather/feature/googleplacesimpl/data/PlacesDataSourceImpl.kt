package com.jin.jjinweather.feature.googleplacesimpl.data

import com.jin.jjinweather.feature.googleplaces.data.GooglePlacesApi
import com.jin.jjinweather.feature.googleplaces.data.PlacesDataSource

class PlacesDataSourceImpl(private val googlePlacesApi: GooglePlacesApi) : PlacesDataSource {
    override suspend fun searchDistrictAt(keyword: String) {
        // do work
    }
}
