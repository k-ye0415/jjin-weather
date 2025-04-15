package com.jin.jjinweather.layer.ui.temperature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.jjinweather.layer.domain.model.UiState
import com.jin.jjinweather.layer.domain.model.weather.Weather
import com.jin.jjinweather.layer.domain.usecase.GetLocationBasedWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TemperatureViewModel(private val getLocationBasedWeatherUseCase: GetLocationBasedWeatherUseCase) : ViewModel() {
    private val _weatherState = MutableStateFlow<UiState<Weather>>(UiState.Loading)
    val weatherState: StateFlow<UiState<Weather>> = _weatherState

    fun onLocationPermissionGranted() {
        fetchWeather()
    }

    private fun fetchWeather() {
        viewModelScope.launch {
            _weatherState.value = getLocationBasedWeatherUseCase()
        }
    }
}
