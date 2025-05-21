package com.jin.jjinweather.feature.outfitrecommend.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.jin.jjinweather.feature.temperature.ui.weathercontent.WeatherLoadingScreen
import com.jin.jjinweather.feature.weather.ui.state.UiState

@Composable
fun OutfitScreen(viewModel: OutfitViewModel, temperature: Int) {
    val outfitImageUrl by viewModel.outfitImageUrl.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadOutfitImageForTemperature(temperature)
    }

    // FIXME : Loading, error 화면 수정 필요.
    when (val state = outfitImageUrl) {
        is UiState.Loading -> WeatherLoadingScreen()
        is UiState.Success -> OutfitRecommendScreen(state.data)
        else -> OutfitRecommendScreen(null)
    }
}
