package com.jin.jjinweather.layer.data.weather

import com.jin.jjinweather.layer.data.database.entity.WeatherEntity
import com.jin.jjinweather.layer.domain.model.weather.Weather

fun Weather.toEntityModel(latitude: Double, longitude: Double): WeatherEntity {
    return WeatherEntity(
        latitude = latitude,
        longitude = longitude,
        cityName = cityName,
        iconCode = iconCode,
        currentTemperature = currentTemperature.toDouble(),
        yesterdayTemperature = yesterdayTemperature.toDouble(),
        minTemperature = minTemperature.toDouble(),
        maxTemperature = maxTemperature.toDouble(),
        hourlyWeatherList = hourlyWeatherList.toString(),
        dailyWeatherList = dailyWeatherList.toString(),
        sunrise = sunrise,
        sunset = sunset,
        moonPhase = moonPhase
    )
}
