package com.jin.jjinweather.feature.outfitrecommend.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jin.jjinweather.R
import com.jin.jjinweather.feature.temperature.ui.weathercontent.WeatherLoadingScreen
import com.jin.jjinweather.feature.weather.ui.state.UiState
import com.jin.jjinweather.ui.theme.ButtonColor
import com.jin.jjinweather.ui.theme.JJinWeatherTheme

@Composable
fun OutfitScreen(viewModel: OutfitViewModel, temperature: Int) {
    val outfitImageUrl by viewModel.outfitImageUrl.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadOutfitImageForTemperature(temperature)
    }

    // FIXME : Loading, error 화면 수정 필요.
    when (val state = outfitImageUrl) {
        is UiState.Loading -> WeatherLoadingScreen()
        else -> OutfitRecommendScreen(state)
    }
}
