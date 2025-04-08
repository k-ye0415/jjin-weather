package com.jin.jjinweather.layer.ui.onboarding

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jin.jjinweather.layer.domain.SaveResult
import com.jin.jjinweather.layer.domain.model.PermissionState
import com.jin.jjinweather.layer.domain.model.UiState
import com.jin.jjinweather.layer.domain.model.weather.Weather
import com.jin.jjinweather.layer.domain.usecase.GetGeoPointUseCase
import com.jin.jjinweather.layer.domain.usecase.GetWeatherUseCase
import com.jin.jjinweather.layer.domain.usecase.SaveWeatherUseCase
import com.jin.jjinweather.layer.domain.usecase.GetCachedLastWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val saveWeatherUseCase: SaveWeatherUseCase,
    private val getCachedLastWeatherUseCase: GetCachedLastWeatherUseCase,
    private val getGeoPointUseCase: GetGeoPointUseCase
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

    /*
    * 1. api 호출
    * 2. api 호출 성공
    *   -> db 저장
    *       -> 저장 성공
    *           -> ui 전달
    *      -> 저장 실패
    *           -> ui 전달하고 error log
    * 3. api 호출 실패
    *   -> db 읽기
    *       -> 읽기 성공
    *           -> ui 전달
    *       -> 읽기 실패
    *           -> 실패한 것 ui 알리고 retry
    * */
    private fun loadWeather() {
        viewModelScope.launch {
            val geoPoint = getGeoPointUseCase()
            val weatherResult = getWeatherUseCase(geoPoint.latitude, geoPoint.longitude)
            if (weatherResult is UiState.Success) {
                val saveResult = saveWeatherUseCase(weatherResult.data)
                when (saveResult) {
                    is SaveResult.Success -> _weatherState.value = weatherResult
                    is SaveResult.Failure -> {
                        _weatherState.value = weatherResult
                        Log.e(TAG, "Database 저장 실패.\n${saveResult.reason}")
                    }
                }
            } else if (weatherResult is UiState.Error) {
                val fetchResult = getCachedLastWeatherUseCase()
                if (fetchResult is UiState.Success) {
                    _weatherState.value = fetchResult
                } else if (fetchResult is UiState.Error) {
                    Log.e(TAG, "Database Data 없음.\n${fetchResult.message}")
                }
            }
        }
    }

    companion object {
        private const val TAG = "OnboardingViewModel"
    }
}
