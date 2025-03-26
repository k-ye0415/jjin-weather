package com.jin.jjinweather.layer.ui.loading

import androidx.lifecycle.ViewModel
import com.jin.jjinweather.layer.domain.model.weather.Weather
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoadingViewModel : ViewModel() {
       private val sampleWeather = Weather(
           cityName = "Seoul",
           iconResId = 9264,
           currentTemperature = 8.9,
           yesterdayTemperature = 10.11,
           minTemperature = 12.13,
           maxTemperature = 14.15,
           hourlyWeatherList = listOf(),
           dailyWeatherList = listOf(),
           sunrise = 9393,
           sunset = 4398,
           moonPhase = 8005
       )
    private val _weather = MutableStateFlow(sampleWeather)
    val weather:StateFlow<Weather> = _weather

    // todo : 앱 첫 실행 여부 판단
    private val _isFirstLaunch = MutableStateFlow(true)
    val isFirstLaunch: StateFlow<Boolean> = _isFirstLaunch

}
