package com.jin.jjinweather.feature.googleplaces.data

import com.jin.jjinweather.feature.googleplaces.domain.PlacesRepository
import com.jin.jjinweather.feature.googleplaces.domain.model.District

class PlacesRepositoryImpl(private val placesDataSource: PlacesDataSource) : PlacesRepository {
    override suspend fun searchDistrictsByKeyword(keyword: String): Result<List<District>> =
        placesDataSource.searchDistrictsByKeyword(keyword)
}
