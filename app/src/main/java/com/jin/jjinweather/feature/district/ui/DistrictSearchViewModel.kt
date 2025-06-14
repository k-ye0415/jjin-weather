package com.jin.jjinweather.feature.district.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.jjinweather.feature.googleplaces.domain.model.District
import com.jin.jjinweather.feature.googleplaces.domain.usecase.SearchDistrictUseCase
import com.jin.jjinweather.feature.location.domain.GetDistrictWithWeatherUseCase
import com.jin.jjinweather.feature.location.domain.SaveDistrictAndRequestWeatherUseCase
import com.jin.jjinweather.feature.weather.domain.model.CityWeather
import com.jin.jjinweather.feature.weather.ui.state.DistrictState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DistrictSearchViewModel(
    private val searchDistrictUseCase: SearchDistrictUseCase,
    private val saveDistrictAndRequestWeatherUseCase: SaveDistrictAndRequestWeatherUseCase,
    getDistrictWithWeatherUseCase: GetDistrictWithWeatherUseCase,
) : ViewModel() {
    val weatherListState: StateFlow<List<CityWeather>> = getDistrictWithWeatherUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val _districtSearchList = MutableStateFlow<DistrictState<List<District>>>(DistrictState.Idle)
    val districtSearchListState: StateFlow<DistrictState<List<District>>> = _districtSearchList

    fun searchDistrictAt(keyword: String) {
        if (keyword.isBlank()) {
            _districtSearchList.value = DistrictState.Idle
            return
        }
        viewModelScope.launch {
            _districtSearchList.value = DistrictState.Loading
            _districtSearchList.value = searchDistrictUseCase(keyword).fold(
                onSuccess = { DistrictState.Success(it) },
                onFailure = { DistrictState.Error(it.message.orEmpty()) }
            )
        }
    }

    fun saveDistrict(pageNumber: Int, district: District) {
        viewModelScope.launch {
            saveDistrictAndRequestWeatherUseCase(pageNumber, district)
        }
    }
}
