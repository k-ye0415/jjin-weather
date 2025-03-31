package com.jin.jjinweather.layer.domain.repository

import com.jin.jjinweather.layer.domain.model.weather.Weather

interface WeatherRepository {
    // 위치 권한 확인 후 사용자의 실제 위도,경도 필요
    suspend fun loadWeather(latitude: Double, longitude: Double): Result<Weather>
}
