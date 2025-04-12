package com.jin.jjinweather.layer.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.jjinweather.layer.domain.model.PermissionState
import com.jin.jjinweather.layer.domain.model.UiState
import com.jin.jjinweather.layer.domain.model.weather.Weather
import com.jin.jjinweather.layer.domain.usecase.GetLocationBasedWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val getLocationBasedWeatherUseCase: GetLocationBasedWeatherUseCase
) : ViewModel() {
    // todo : 앱 첫 실행 여부 판단
    private val _isFirstLaunch = MutableStateFlow(true)
    val isFirstLaunch: StateFlow<Boolean> = _isFirstLaunch

    private val _locationPermissionState = MutableStateFlow(PermissionState.DENIED)
    val locationPermissionState: StateFlow<PermissionState> = _locationPermissionState

    private val _weatherState = MutableStateFlow<UiState<Weather>>(UiState.Loading)
    val weatherState: StateFlow<UiState<Weather>> = _weatherState

    fun onLocationPermissionGranted() {
        _locationPermissionState.value = PermissionState.GRANTED
        loadWeather()
    }

    private fun loadWeather() {
        viewModelScope.launch {
            _weatherState.value = getLocationBasedWeatherUseCase()
        }
    }

    companion object {
        private const val TAG = "OnboardingViewModel"
    }
}
