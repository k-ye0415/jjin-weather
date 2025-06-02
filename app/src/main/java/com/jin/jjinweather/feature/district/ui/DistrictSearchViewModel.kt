package com.jin.jjinweather.feature.district.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.jjinweather.feature.googleplaces.domain.model.District
import com.jin.jjinweather.feature.googleplaces.domain.usecase.SearchDistrictUseCase
import com.jin.jjinweather.feature.weather.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DistrictSearchViewModel(private val searchDistrictUseCase: SearchDistrictUseCase) : ViewModel() {
    private val _districtList = MutableStateFlow<UiState<List<District>>>(UiState.Loading)
    val districtList: StateFlow<UiState<List<District>>> = _districtList

    fun searchDistrictAt(keyword: String) {
        viewModelScope.launch {
            _districtList.value = searchDistrictUseCase(keyword).fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }
}
