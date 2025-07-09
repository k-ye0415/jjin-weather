package com.jin.jjinweather.feature.district.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.jjinweather.feature.googleplaces.domain.model.District
import com.jin.jjinweather.feature.googleplaces.domain.usecase.SearchDistrictUseCase
import com.jin.jjinweather.feature.location.domain.SaveDistrictAndRequestWeatherUseCase
import com.jin.jjinweather.feature.weather.domain.model.CityWeather
import com.jin.jjinweather.feature.weather.domain.usecase.GetAllLocationAndWeatherUseCase
import com.jin.jjinweather.feature.weather.ui.state.SearchState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DistrictSearchViewModel(
    private val searchDistrictUseCase: SearchDistrictUseCase,
    private val saveDistrictAndRequestWeatherUseCase: SaveDistrictAndRequestWeatherUseCase,
    getAllLocationAndWeatherUseCase: GetAllLocationAndWeatherUseCase,
) : ViewModel() {
    val weatherListState: StateFlow<List<CityWeather>> = getAllLocationAndWeatherUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private val _districtSearchList = MutableStateFlow<SearchState<List<District>>>(SearchState.Idle)
    val districtSearchListState: StateFlow<SearchState<List<District>>> = _districtSearchList

    private val _keyword = MutableStateFlow("")
    val keyword: StateFlow<String> = _keyword

    init {
        viewModelScope.launch {
            keyword
                .debounce(300)
                .distinctUntilChanged()
                .filter { it.isNotBlank() }
                .collectLatest { query -> searchDistrictAt(query) }
        }
    }

    fun updateKeyword(newKeyword: String) {
        _keyword.value = newKeyword
        if (newKeyword.isBlank()) {
            _districtSearchList.value = SearchState.Idle
        }
    }

    private fun searchDistrictAt(keyword: String) {
        if (keyword.isBlank()) {
            _districtSearchList.value = SearchState.Idle
            return
        }
        viewModelScope.launch {
            _districtSearchList.value = SearchState.Loading
            _districtSearchList.value = searchDistrictUseCase(keyword).fold(
                onSuccess = { SearchState.Success(it) },
                onFailure = { SearchState.Error(it.message.orEmpty()) }
            )
        }
    }

    fun saveDistrict(pageNumber: Int, district: District) {
        viewModelScope.launch {
            saveDistrictAndRequestWeatherUseCase(pageNumber, district)
        }
    }
}
