package com.jin.jjinweather.feature.outfitrecommend.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.jin.jjinweather.feature.temperature.ui.weathercontent.WeatherLoadingScreen
import com.jin.jjinweather.feature.weather.domain.model.HourlyForecast
import com.jin.jjinweather.feature.weather.ui.state.UiState

@Composable
fun OutfitScreen(
    viewModel: OutfitViewModel,
    temperature: Int,
    cityName: String,
    summary: String,
    forecast: HourlyForecast
) {
    val outfitImageUrl by viewModel.outfitImageUrl.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadOutfitImageForTemperature(temperature)
    }

    when (val state = outfitImageUrl) {
        is UiState.Loading -> WeatherLoadingScreen()
        is UiState.Success -> OutfitRecommendScreen(state.data, cityName, summary, forecast)
        else -> OutfitRecommendScreen(null, cityName, summary, forecast)
    }
}
