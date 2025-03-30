package com.jin.jjinweather.layer.data.location

import android.content.Context

class LocationProvider(private val context: Context) {
    suspend fun loadCurrentCityName(): String {
        // 권한 획득 및 위치 정보 로직 수정될 수 있음.
        return "서울시 방배동"
    }
}
