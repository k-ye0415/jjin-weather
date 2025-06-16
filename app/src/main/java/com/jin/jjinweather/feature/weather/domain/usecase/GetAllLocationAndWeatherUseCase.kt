package com.jin.jjinweather.feature.weather.domain.usecase

import com.jin.jjinweather.feature.location.LocationRepository
import com.jin.jjinweather.feature.weather.domain.model.CityWeather
import com.jin.jjinweather.feature.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

class GetAllLocationAndWeatherUseCase(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<CityWeather>> = flow {
        // 1. 현재 위치 갱신
        val currentGeoPoint = locationRepository.currentGeoPoint()
        locationRepository.findCityNameAt(currentGeoPoint)

        emit(Unit)
    }.flatMapLatest {
        combine(
            locationRepository.fetchGeoPoints(),
            locationRepository.fetchCityNames()
        ) { geoPoints, cityNames ->
            // 갱신된 현재 위치와 DB 저장된 위치
            val geoPointMap = geoPoints.associateBy { it.pageNumber }
            val cityNameMap = cityNames.associateBy { it.pageNumber }
            val commonPageIndexes = geoPointMap.keys intersect cityNameMap.keys

            commonPageIndexes.mapNotNull { index ->
                val geo = geoPointMap[index]
                val city = cityNameMap[index]
                if (geo != null && city != null) index to (geo to city) else null
            }
        }.flatMapLatest { indexedGeoCityPairs ->
            flow {
                val result = indexedGeoCityPairs.mapNotNull { (index, location) ->
                    val (geo, city) = location
                    val weather = weatherRepository.weatherAt(
                        pageNumber = index ?: 0,
                        latitude = geo.latitude,
                        longitude = geo.longitude
                    ).getOrNull()

                    if (weather != null) {
                        CityWeather(index ?: 0, city.name, weather)
                    } else null
                }
                emit(result)
            }
        }
    }
}
