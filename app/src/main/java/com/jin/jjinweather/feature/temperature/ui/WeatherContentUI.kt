package com.jin.jjinweather.feature.temperature.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.jjinweather.R
import com.jin.jjinweather.feature.weather.ui.state.UiState
import com.jin.jjinweather.feature.weather.domain.model.CityWeather

@Composable
fun WeatherContentUI(cityWeather: UiState<CityWeather>) {
    when (cityWeather) {
        is UiState.Loading -> WeatherLoadingContent()
        is UiState.Success -> WeatherLoadedContent(cityWeather.data)
        is UiState.Error -> WeatherErrorContent(cityWeather.message)
    }
}

@Composable
fun WeatherLoadingContent() {
    // indicator
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), contentAlignment = Alignment.Center
        ) {
            Column {
                Text("Loading...", fontSize = 24.sp)
            }
        }
    }
}

@Composable
fun WeatherLoadedContent(cityWeather: CityWeather) {
    // ui 수정 필요
    val currentWeatherIconRes = cityWeather.weather.dayWeather.icon.drawableRes

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), contentAlignment = Alignment.Center
        ) {
            Column {
                Text("위치 : ${cityWeather.cityName}")
                Text("현재 온도 : ${cityWeather.weather.dayWeather.temperature}")
                Image(
                    painter = painterResource(id = currentWeatherIconRes),
                    contentDescription = "current temp icon",
                    modifier = Modifier.size(64.dp)
                )
                Text("시간별 예보")
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(cityWeather.weather.forecast.hourly.size) { index ->
                        val hourlyWeatherIconRes =cityWeather.weather.forecast.hourly[index].icon.drawableRes
                        Column {
                            Image(
                                painter = painterResource(id = hourlyWeatherIconRes),
                                contentDescription = "hourly temp icon",
                                modifier = Modifier.size(32.dp)
                            )
                            Text("${cityWeather.weather.forecast.hourly[index].temperature}")
                        }
                    }
                }
                Text("일별 예보")
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(cityWeather.weather.forecast.daily.size) { index ->
                        val dailyWeatherIconRes =cityWeather.weather.forecast.daily[index].icon.drawableRes
                        Column {
                            Image(
                                painter = painterResource(id = dailyWeatherIconRes),
                                contentDescription = "hourly temp icon",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherErrorContent(message: String) {
    // ui 수정 필요
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), contentAlignment = Alignment.Center
        ) {
            Column {
                Text("Error : $message", color = Color.Red)
            }
        }
    }
}
