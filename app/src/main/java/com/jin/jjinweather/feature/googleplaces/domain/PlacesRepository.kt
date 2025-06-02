package com.jin.jjinweather.feature.googleplaces.domain

import com.jin.jjinweather.feature.googleplaces.data.model.Prediction
import com.jin.jjinweather.feature.googleplaces.domain.model.District

interface PlacesRepository {
    suspend fun searchDistrictAt(keyword: String): List<District>
    suspend fun searchDistrictDetail(predictions: List<Prediction>): List<District>
}
