package com.jin.jjinweather.feature.temperature.ui

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.jin.jjinweather.feature.temperature.ui.weathercontent.WeatherErrorScreen
import com.jin.jjinweather.feature.temperature.ui.weathercontent.WeatherLoadingScreen
import com.jin.jjinweather.feature.temperature.ui.weathercontent.success.WeatherSuccessScreen
import com.jin.jjinweather.feature.weather.domain.model.CityWeather
import com.jin.jjinweather.feature.weather.ui.state.UiState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun TemperatureScreen(viewModel: TemperatureViewModel, onNavigateToOutfit: (Int) -> Unit) {
    val composePermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val weather by viewModel.weatherState.collectAsState()

    LaunchedEffect(composePermissionState.status) {
        if (composePermissionState.status is PermissionStatus.Granted) {
            viewModel.onLocationPermissionGranted()
        }
    }
    // FIXME : 불필요한 함수 분리로 판단되어 OutfitScreen UI 수정 시 수정 예정
    WeatherContentUI(weather, onNavigateToOutfit)
}

@Composable
private fun WeatherContentUI(weather: UiState<CityWeather>, onNavigateToOutfit: (Int) -> Unit) {
    when (weather) {
        is UiState.Loading -> WeatherLoadingScreen()
        is UiState.Success -> {
            // FIXME : Weather page 마다 weather 정보 필요(잠시 동일 데이터 적용)
            val weatherList = listOf(weather.data, weather.data)
            val pagerState = rememberPagerState { weatherList.size }
            Box(modifier = Modifier.fillMaxSize()) {
                HorizontalPager(
                    state = pagerState
                ) { page ->
                    WeatherSuccessScreen(
                        weather = weatherList[page],
                        pageCount = pagerState.pageCount,
                        currentPage = pagerState.currentPage,
                        onNavigateToOutfit = onNavigateToOutfit,
                    )
                }
            }
        }
        is UiState.Error -> WeatherErrorScreen(weather.message)
    }
}
