package com.jin.jjinweather.layer.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.jjinweather.layer.domain.model.PermissionState
import com.jin.jjinweather.layer.domain.model.UiState
import com.jin.jjinweather.layer.domain.model.weather.Weather
import com.jin.jjinweather.layer.domain.usecase.GetGeoPointUseCase
import com.jin.jjinweather.layer.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OnboardingViewModel(private val getWeatherUseCase: GetWeatherUseCase, private val getGeoPointUseCase: GetGeoPointUseCase) : ViewModel() {
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
            val geoPoint = getGeoPointUseCase()
            _weatherState.value = getWeatherUseCase(geoPoint.latitude, geoPoint.longitude)
        }
    }
}
