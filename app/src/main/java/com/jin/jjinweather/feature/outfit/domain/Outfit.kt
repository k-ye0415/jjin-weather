package com.jin.jjinweather.feature.outfit.domain

import com.jin.jjinweather.feature.weather.domain.model.CityWeather

data class Outfit(
    val cityWeather: CityWeather,
    val outfitImgTypes: List<String>
)
