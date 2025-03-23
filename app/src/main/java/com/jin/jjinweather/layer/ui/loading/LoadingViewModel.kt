package com.jin.jjinweather.layer.ui.loading

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoadingViewModel : ViewModel() {
    // todo : api 순서
    //      1. 위치
    //      2. 날씨 정보
    private val _location = MutableStateFlow("")
    val location: StateFlow<String> = _location

    private val _weather = MutableStateFlow("")
    val weather: StateFlow<String> = _weather

    // todo : 앱 첫 실행 여부 판단
    private val _isFirstLaunch = MutableStateFlow(true)
    val isFirstLaunch: StateFlow<Boolean> = _isFirstLaunch

}
