package com.jin.jjinweather.feature.weather.domain.usecase

import com.jin.jjinweather.feature.location.LocationRepository
import com.jin.jjinweather.feature.weather.domain.model.CityWeather
import com.jin.jjinweather.feature.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetEveryLocationAndWeatherUseCase(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(): Flow<List<CityWeather>> = flow {
        // 현재 위치 정보는 새롭게 요청
        val currentGeoPoint = locationRepository.currentGeoPoint()
        locationRepository.findCityNameAt(currentGeoPoint)

        // 새롭게 요청 된 현재 정보와 DB 에 저장된 위치에 대한 날씨 요청
        val geoPointMap = locationRepository.fetchGeoPoints().associateBy { it.pageNumber }
        val cityNameMap = locationRepository.fetchCityNames().associateBy { it.pageNumber }
        val commonPageIndexes = geoPointMap.keys intersect cityNameMap.keys

        val result = commonPageIndexes.mapNotNull { index ->
            val geo = geoPointMap[index]
            val city = cityNameMap[index]

            if (geo != null && city != null) {
                val weather = weatherRepository.weatherAt(city.pageNumber, geo.latitude, geo.longitude).getOrNull()
                if (weather != null) {
                    CityWeather(city.pageNumber, city.name, weather)
                } else null
            } else null
        }
        emit(result)
    }
}
