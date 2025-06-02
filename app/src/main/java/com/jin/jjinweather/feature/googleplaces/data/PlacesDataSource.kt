package com.jin.jjinweather.feature.googleplaces.data

import com.jin.jjinweather.feature.googleplaces.data.model.Prediction
import com.jin.jjinweather.feature.googleplaces.domain.model.District

interface PlacesDataSource {
    suspend fun searchDistrictAt(keyword: String): List<Prediction>
    suspend fun searchDistrictDetailAt(prediction: Prediction): District
}
