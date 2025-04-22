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
import com.jin.jjinweather.layer.domain.model.UiState
import com.jin.jjinweather.feature.weather.Weather

@Composable
fun WeatherContentUI(weather: UiState<Weather>) {
    when (weather) {
        is UiState.Loading -> WeatherLoadingContent()
        is UiState.Success -> WeatherLoadedContent(weather.data)
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
fun WeatherLoadedContent(weather: Weather) {
    // ui 수정 필요
    val currentWeatherIconRes = mapWeatherIconToDrawable(weather.iconCode)

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
                    items(weather.hourlyWeatherList.size) { index ->
                        val hourlyWeatherIconRes = mapWeatherIconToDrawable(weather.hourlyWeatherList[index].iconCode)
                        Column {
                            Image(
                                painter = painterResource(id = hourlyWeatherIconRes),
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
                        val dailyWeatherIconRes = mapWeatherIconToDrawable(weather.dailyWeatherList[index].iconCode)
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

private fun mapWeatherIconToDrawable(icon: String): Int {
    return when (icon) {
        "01d" -> R.drawable.ic_main_clear_sky_day
        "01n" -> R.drawable.ic_main_clear_sky_night
        "02d" -> R.drawable.ic_main_few_clouds_day
        "02n" -> R.drawable.ic_main_few_clouds_night
        "03d", "03n", "04d", "04n" -> R.drawable.ic_main_scattered_clouds
        "09d", "09n" -> R.drawable.ic_main_shower_rain
        "10d" -> R.drawable.ic_main_rain_day
        "10n" -> R.drawable.ic_main_rain_night
        "11d", "11n" -> R.drawable.ic_main_thunderstorm
        "13d", "13n" -> R.drawable.ic_main_snow
        "50d", "50n" -> R.drawable.ic_main_mist
        else -> R.drawable.ic_main_clear_sky_day
    }
}
