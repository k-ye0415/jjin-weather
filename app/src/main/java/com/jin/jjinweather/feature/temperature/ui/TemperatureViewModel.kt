package com.jin.jjinweather.feature.temperature.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.jjinweather.feature.location.domain.GetDistrictWithWeatherUseCase
import com.jin.jjinweather.feature.weather.domain.model.CityWeather
import com.jin.jjinweather.feature.weather.domain.usecase.GetCurrentLocationWeatherUseCase
import com.jin.jjinweather.feature.weather.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TemperatureViewModel(
    private val getCurrentLocationWeatherUseCase: GetCurrentLocationWeatherUseCase,
    getDistrictWithWeatherUseCase: GetDistrictWithWeatherUseCase
) :
    ViewModel() {
    private val _weatherState = MutableStateFlow<UiState<CityWeather>>(UiState.Loading)
    val weatherState: StateFlow<UiState<CityWeather>> = _weatherState

    val weatherListState: StateFlow<List<CityWeather>> = getDistrictWithWeatherUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun onLocationPermissionGranted(pageNumber: Int) {
        fetchWeather(pageNumber)
    }

    private fun fetchWeather(pageNumber: Int) {
        viewModelScope.launch {
            _weatherState.value = getCurrentLocationWeatherUseCase(pageNumber).fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }
}
