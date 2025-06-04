package com.jin.jjinweather.feature.googleplaces.domain

import com.jin.jjinweather.feature.googleplaces.domain.model.District

interface PlacesRepository {
    suspend fun searchDistrictsByKeyword(keyword: String): Result<List<District>>
}
