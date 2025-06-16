package com.jin.jjinweather.feature.temperature.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.jjinweather.feature.weather.domain.model.CityWeather
import com.jin.jjinweather.feature.weather.domain.usecase.GetEveryLocationAndWeatherUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class TemperatureViewModel(
    getEveryLocationAndWeatherUseCase: GetEveryLocationAndWeatherUseCase
) : ViewModel() {

    val weatherListState: StateFlow<List<CityWeather>> = getEveryLocationAndWeatherUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

}
