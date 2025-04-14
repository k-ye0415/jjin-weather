package com.jin.jjinweather.layer.data.repository

import com.jin.jjinweather.layer.data.database.dao.WeatherDAO
import com.jin.jjinweather.layer.data.database.entity.WeatherEntity
import com.jin.jjinweather.layer.data.weather.WeatherDataSource
import com.jin.jjinweather.layer.domain.model.UiState
import com.jin.jjinweather.layer.domain.model.weather.Weather
import com.jin.jjinweather.layer.domain.repository.WeatherRepository

class WeatherRepositoryImpl(
    private val weatherDAO: WeatherDAO,
    private val weatherDataSource: WeatherDataSource
) : WeatherRepository {

    override suspend fun weatherAt(latitude: Double, longitude: Double): UiState<Weather> {
        return weatherDataSource.requestWeatherAt(latitude, longitude).fold(
            onSuccess = { weather ->
                weatherDAO.insertWeather(weather.toEntityModel())
                UiState.Success(weather)
            },
            onFailure = {
                val weather = weatherDAO.findLatestWeather()
                if (weather != null) UiState.Success(weather.toDomainModel())
                else UiState.Error(it.message.orEmpty())
            }
        )
    }

    /**
     * Domain layer Model -> Data layer Model 변환
     * */
    private fun Weather.toEntityModel(): WeatherEntity {
        return WeatherEntity(
            cityName = cityName,
            iconCode = iconCode,
            currentTemperature = currentTemperature.toDouble(),
            yesterdayTemperature = yesterdayTemperature.toDouble(),
            minTemperature = minTemperature.toDouble(),
            maxTemperature = maxTemperature.toDouble(),
            hourlyWeatherList = hourlyWeatherList,
            dailyWeatherList = dailyWeatherList,
            sunrise = sunrise,
            sunset = sunset,
            moonPhase = moonPhase
        )
    }

    /**
     * Data layer Model -> Domain layer Model 변환
     * */
    private fun WeatherEntity.toDomainModel(): Weather {
        return Weather(
            cityName = cityName,
            iconCode = iconCode,
            currentTemperature = currentTemperature,
            yesterdayTemperature = yesterdayTemperature,
            minTemperature = minTemperature,
            maxTemperature = maxTemperature,
            hourlyWeatherList = hourlyWeatherList,
            dailyWeatherList = dailyWeatherList,
            sunrise = sunrise,
            sunset = sunset,
            moonPhase = moonPhase
        )
    }

    companion object {
        private const val TAG = "WeatherRepository"
    }
}
