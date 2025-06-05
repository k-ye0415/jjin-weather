package com.jin.jjinweather.feature.district.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.jjinweather.feature.googleplaces.domain.model.District
import com.jin.jjinweather.feature.googleplaces.domain.usecase.SearchDistrictUseCase
import com.jin.jjinweather.feature.weather.ui.state.DistrictState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DistrictSearchViewModel(private val searchDistrictUseCase: SearchDistrictUseCase) : ViewModel() {
    private val _districtList = MutableStateFlow<DistrictState<List<District>>>(DistrictState.Idle)
    val districtList: StateFlow<DistrictState<List<District>>> = _districtList

    fun searchDistrictAt(keyword: String) {
        if (keyword.isBlank()) {
            _districtList.value = DistrictState.Idle
            return
        }
        viewModelScope.launch {
            _districtList.value = DistrictState.Loading
            _districtList.value = searchDistrictUseCase(keyword).fold(
                onSuccess = { DistrictState.Success(it) },
                onFailure = { DistrictState.Error(it.message.orEmpty()) }
            )
        }
    }
}
