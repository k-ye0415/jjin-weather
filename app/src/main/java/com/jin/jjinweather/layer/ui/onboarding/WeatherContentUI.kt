package com.jin.jjinweather.layer.ui.onboarding

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
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.jjinweather.layer.domain.model.UiState
import com.jin.jjinweather.layer.domain.model.weather.Weather

@Composable
fun WeatherContentUI(weather: UiState<Weather>, onNavigateToTemperature: () -> Unit) {
    when (weather) {
        is UiState.Loading -> WeatherLoadingContent()
        is UiState.Success -> WeatherLoadedContent(weather.data, onNavigateToTemperature)
        is UiState.Error -> WeatherErrorContent(weather.message)
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
fun WeatherLoadedContent(weather: Weather, onNavigateToOnboarding: () -> Unit) {
    // success 한 후, Main 화면으로 자동으로 이동할 수 있도록 처리필요.
    // 임시 UI 작성. Main 화면으로 이동 되면 다듬어질 예정.
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), contentAlignment = Alignment.Center
        ) {
            Column {
                Text("위치 : ${weather.cityName}")
                Text("현재 온도 : ${weather.currentTemperature}")
                Image(
                    painter = painterResource(id = weather.iconResId),
                    contentDescription = "current temp icon",
                    modifier = Modifier.size(64.dp)
                )
                Text("시간별 예보")
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(weather.hourlyWeatherList.size) { index ->
                        Column {
                            Image(
                                painter = painterResource(id = weather.hourlyWeatherList[index].iconResId),
                                contentDescription = "hourly temp icon",
                                modifier = Modifier.size(32.dp)
                            )
                            Text("${weather.hourlyWeatherList[index].temperature}")
                        }
                    }
                }
                Text("일별 예보")
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(weather.dailyWeatherList.size) { index ->
                        Column {
                            Image(
                                painter = painterResource(id = weather.dailyWeatherList[index].iconResId),
                                contentDescription = "hourly temp icon",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
                Button(onClick = onNavigateToOnboarding) { Text("GO TO MAIN") }
            }
        }
    }
}

@Composable
fun WeatherErrorContent(message: String) {
    // 적절한 예외처리 필요
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
