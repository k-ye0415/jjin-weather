package com.jin.jjinweather.feature.temperature.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.jjinweather.feature.weather.domain.model.CityWeather
import com.jin.jjinweather.feature.weather.domain.usecase.GetCurrentLocationWeatherUseCase
import com.jin.jjinweather.feature.weather.ui.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TemperatureViewModel(private val getCurrentLocationWeatherUseCase: GetCurrentLocationWeatherUseCase) :
    ViewModel() {
    private val _weatherState = MutableStateFlow<UiState<CityWeather>>(UiState.Loading)
    val weatherState: StateFlow<UiState<CityWeather>> = _weatherState

    fun onLocationPermissionGranted() {
        fetchWeather()
    }

    private fun fetchWeather() {
        viewModelScope.launch {
            _weatherState.value = getCurrentLocationWeatherUseCase().fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message.orEmpty()) }
            )
        }
    }
}
