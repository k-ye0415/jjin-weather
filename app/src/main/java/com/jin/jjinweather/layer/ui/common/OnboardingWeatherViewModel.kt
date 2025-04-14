package com.jin.jjinweather.layer.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.jjinweather.layer.domain.model.PermissionState
import com.jin.jjinweather.layer.domain.model.UiState
import com.jin.jjinweather.layer.domain.model.weather.Weather
import com.jin.jjinweather.layer.domain.repository.PreferencesRepository
import com.jin.jjinweather.layer.domain.usecase.GetLocationBasedWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OnboardingWeatherViewModel(
    private val repository: PreferencesRepository,
    private val getLocationBasedWeatherUseCase: GetLocationBasedWeatherUseCase
) : ViewModel() {
    private val _permissionState = MutableStateFlow(PermissionState.DENIED)
    val permissionState: StateFlow<PermissionState> = _permissionState

    private val _weatherState = MutableStateFlow<UiState<Weather>>(UiState.Loading)
    val weatherState: StateFlow<UiState<Weather>> = _weatherState

    private var isFirstLaunch = true

    init {
        checkIfFirstLaunch()
    }

    private fun checkIfFirstLaunch() {
        viewModelScope.launch {
            isFirstLaunch = repository.isFirstLaunch()
        }
    }

    fun onLocationPermissionGranted() {
        viewModelScope.launch {
            _permissionState.value = PermissionState.GRANTED
            if (isFirstLaunch) {
                repository.completeFirstLaunch()
            }
        }
    }

    fun onTemperatureScreenEntered() {
        fetchWeather()
    }

    private fun fetchWeather() {
        viewModelScope.launch {
            _weatherState.value = getLocationBasedWeatherUseCase()
        }
    }
}
