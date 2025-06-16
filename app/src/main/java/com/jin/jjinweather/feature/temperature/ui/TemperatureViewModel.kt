package com.jin.jjinweather.feature.temperature.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.jjinweather.feature.weather.domain.model.CityWeather
import com.jin.jjinweather.feature.weather.domain.usecase.GetAllLocationAndWeatherUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class TemperatureViewModel(getAllLocationAndWeatherUseCase: GetAllLocationAndWeatherUseCase) : ViewModel() {

    val weatherListState: StateFlow<List<CityWeather>> = getAllLocationAndWeatherUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}
