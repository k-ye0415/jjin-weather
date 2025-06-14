package com.jin.jjinweather.feature.location.domain

import com.jin.jjinweather.feature.location.LocationRepository
import com.jin.jjinweather.feature.weather.domain.model.CityWeather
import com.jin.jjinweather.feature.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetDistrictWithWeatherUseCase(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(): Flow<List<CityWeather>> = flow {
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
