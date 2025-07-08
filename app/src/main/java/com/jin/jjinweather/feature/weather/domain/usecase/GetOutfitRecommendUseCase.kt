package com.jin.jjinweather.feature.weather.domain.usecase

import com.jin.jjinweather.feature.location.LocationRepository
import com.jin.jjinweather.feature.outfit.domain.Outfit
import com.jin.jjinweather.feature.outfit.domain.OutfitRepository
import com.jin.jjinweather.feature.weather.domain.model.CityWeather
import com.jin.jjinweather.feature.weather.domain.repository.WeatherRepository

class GetOutfitRecommendUseCase(
    private val locationRepository: LocationRepository,
    private val weatherRepository: WeatherRepository,
    private val outfitRepository: OutfitRepository
) {
    suspend operator fun invoke(pageNumber: Int): Result<Outfit> {
        val cityName = locationRepository.findCityNameByPageNumber(pageNumber)
        val weather = weatherRepository.findWeatherByPageNumber(pageNumber)

        if (cityName.isEmpty()) return Result.failure(Exception("City name is empty"))
        if (weather == null) return Result.failure(Exception("Weather is null"))

        val cityWeather = CityWeather(pageNumber, cityName, weather)
        val temperature = weather.dayWeather.temperature.toInt()
        val feelsLikeTemperature = weather.dayWeather.feelsLikeTemperature.toInt()
        return outfitRepository.fetchOutfitImgTypeByTemperature(temperature, feelsLikeTemperature).fold(
            onSuccess = { Result.success(Outfit(cityWeather, it)) },
            onFailure = { Result.failure(it) }
        )
    }
}
