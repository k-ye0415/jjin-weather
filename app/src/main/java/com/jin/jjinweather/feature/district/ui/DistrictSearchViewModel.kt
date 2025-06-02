package com.jin.jjinweather.feature.district.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.jjinweather.feature.googleplaces.domain.model.District
import com.jin.jjinweather.feature.googleplaces.domain.usecase.SearchDistrictUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DistrictSearchViewModel(private val searchDistrictUseCase: SearchDistrictUseCase) : ViewModel() {
    private val _districtList = MutableStateFlow<List<District>>(emptyList())
    val districtList: StateFlow<List<District>> = _districtList

    fun searchDistrict(keyword: String) {
        viewModelScope.launch {
            _districtList.value = searchDistrictUseCase(keyword)
        }
    }
}
