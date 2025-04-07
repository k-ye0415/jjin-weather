package com.jin.jjinweather.layer.data.weather

import com.jin.jjinweather.layer.data.database.entity.WeatherEntity
import com.jin.jjinweather.layer.domain.model.weather.Weather

/**
 * Domain layer Model -> Data layer Model 변환
 * */
fun Weather.toEntityModel(): WeatherEntity {
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
fun WeatherEntity.toDomainModel(): Weather {
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
