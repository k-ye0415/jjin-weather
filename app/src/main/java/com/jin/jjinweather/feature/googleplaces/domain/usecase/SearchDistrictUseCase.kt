package com.jin.jjinweather.feature.googleplaces.domain.usecase

import com.jin.jjinweather.feature.googleplaces.domain.PlacesRepository
import com.jin.jjinweather.feature.googleplaces.domain.model.District

class SearchDistrictUseCase(private val repository: PlacesRepository) {
    suspend operator fun invoke(keyword: String): Result<List<District>> {
        return repository.searchDistrictsByKeyword(keyword)
    }
}
