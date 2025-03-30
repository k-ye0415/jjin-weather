package com.jin.jjinweather.layer.domain.repository

import com.jin.jjinweather.layer.domain.model.weather.Weather

interface WeatherRepository {
    // 위치 정보 가져오는 부분에 따라 수정될 수 있음.
    suspend fun loadWeather(): Result<Weather>
}
