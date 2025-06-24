package com.jin.jjinweather.feature.temperature.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.jin.jjinweather.feature.temperature.ui.weathercontent.WeatherLoadingScreen
import com.jin.jjinweather.feature.temperature.ui.weathercontent.success.WeatherSuccessScreen
import com.jin.jjinweather.feature.weather.domain.model.HourlyForecast

@Composable
fun TemperatureScreen(
    viewModel: TemperatureViewModel,
    onNavigateToOutfit: (pageNumber: Int) -> Unit,
    onNavigateToDistrict: () -> Unit
) {
    val weatherListState by viewModel.weatherListState.collectAsState()

    if (weatherListState.isEmpty()) {
        WeatherLoadingScreen()
    } else {
        val pagerState = rememberPagerState { weatherListState.size }
        Box(modifier = Modifier.fillMaxSize()) {
            HorizontalPager(
                state = pagerState
            ) { page ->
                WeatherSuccessScreen(
                    weather = weatherListState[page],
                    pageCount = pagerState.pageCount,
                    currentPage = pagerState.currentPage,
                    onNavigateToOutfit = onNavigateToOutfit,
                    onNavigateToDistrict = onNavigateToDistrict
                )
            }
        }
    }
}
