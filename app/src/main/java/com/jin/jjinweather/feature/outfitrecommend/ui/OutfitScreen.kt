package com.jin.jjinweather.feature.outfitrecommend.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.jin.jjinweather.feature.outfitrecommend.ui.recommendcontent.OutfitRecommendScreen
import com.jin.jjinweather.feature.temperature.ui.weathercontent.WeatherErrorScreen
import com.jin.jjinweather.feature.weather.ui.state.UiState

@Composable
fun OutfitScreen(
    viewModel: OutfitViewModel,
    pageNumber: Int,
    onNavigateToTemperature: () -> Unit
) {
    val outfitState by viewModel.outfitState.collectAsState()

    LaunchedEffect(pageNumber) {
        viewModel.loadOutfitByPageNumber(pageNumber)
    }

    when (val state = outfitState) {
        is UiState.Loading -> OutfitLoadingScreen()
        is UiState.Success -> {
            val cityWeather = state.data.cityWeather
            val outfitTypes = state.data.outfitImgTypes
            OutfitRecommendScreen(
                outfitTypes = outfitTypes,
                cityName = cityWeather.cityName,
                summary = cityWeather.weather.forecast.daily.firstOrNull()?.summary.orEmpty(),
                timeZoneId = cityWeather.weather.timeZone,
                forecast = cityWeather.weather.forecast.hourly,
                feelsLikeTemperature = cityWeather.weather.yesterdayWeather.temperature.toInt(),
                onNavigateToTemperature = onNavigateToTemperature
            )
        }

        is UiState.Error -> WeatherErrorScreen(state.message)
    }
}
