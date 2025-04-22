package com.jin.jjinweather.feature.weather.data

import android.database.SQLException
import com.jin.jjinweather.feature.location.data.GeoCodeDataSource
import com.jin.jjinweather.layer.data.database.entity.WeatherEntity
import com.jin.jjinweather.layer.domain.model.UiState
import com.jin.jjinweather.layer.domain.model.weather.Weather
import com.jin.jjinweather.feature.weather.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(
    private val weatherTrackingDataSource: WeatherTrackingDataSource,
    private val weatherDataSource: WeatherDataSource,
    private val geoCodeDataSource: GeoCodeDataSource,
) : WeatherRepository {

    override suspend fun weatherAt(latitude: Double, longitude: Double): UiState<Weather> =
        weatherDataSource.requestWeatherAt(latitude, longitude)
            .map {
                val cityName = geoCodeDataSource.findCityNameAt(latitude, longitude)
                val weather = it.copy(cityName = cityName)

                keepTrackWeatherChanges(weather)
                UiState.Success(weather)
            }
            .getOrElse {
                val weather = queryLatestWeather() ?: return UiState.Error(it.message.orEmpty())
                return UiState.Success(weather.toDomainModel())
            }


    private suspend fun queryLatestWeather(): WeatherEntity? {
        return try {
            withContext(Dispatchers.IO) {
                weatherTrackingDataSource.latestWeatherOrNull()
            }
        } catch (_: SQLException) {
            null
        }
    }

    private suspend fun keepTrackWeatherChanges(weather: Weather) {
        try {
            withContext(Dispatchers.IO) {
                weatherTrackingDataSource.markAsLatestWeather(weather.toEntityModel())
            }
        } catch (_: Exception) {
            // Silently ignore the error.
        }
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

    private companion object {
        const val TAG = "WeatherRepository"
    }
}
