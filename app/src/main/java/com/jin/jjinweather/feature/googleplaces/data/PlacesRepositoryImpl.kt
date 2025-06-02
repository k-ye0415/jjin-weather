package com.jin.jjinweather.feature.googleplaces.data

import com.jin.jjinweather.feature.googleplaces.data.model.Prediction
import com.jin.jjinweather.feature.googleplaces.domain.PlacesRepository
import com.jin.jjinweather.feature.googleplaces.domain.model.District

class PlacesRepositoryImpl(private val placesDataSource: PlacesDataSource) : PlacesRepository {
    override suspend fun searchDistrictAt(keyword: String): List<District> {
        val prediction = placesDataSource.searchDistrictAt(keyword)
        return searchDistrictDetail(prediction)
    }

    override suspend fun searchDistrictDetail(predictions: List<Prediction>): List<District> {
        return predictions.map {
            placesDataSource.searchDistrictDetailAt(it)
        }
    }
}
