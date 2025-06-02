package com.jin.jjinweather.feature.googleplaces.data

import com.jin.jjinweather.feature.googleplaces.domain.model.District

interface PlacesDataSource {
    suspend fun searchDistrictsByKeyword(keyword: String): Result<List<District>>
    suspend fun fetchDistrictDetailByPlaceId(placeId: String): Result<District>
}
