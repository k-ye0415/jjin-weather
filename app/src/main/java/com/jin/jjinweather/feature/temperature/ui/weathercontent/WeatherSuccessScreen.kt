package com.jin.jjinweather.feature.temperature.ui.weathercontent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jin.jjinweather.feature.temperature.ui.weathercontent.success.TopMenuAction
import com.jin.jjinweather.feature.weather.domain.model.CityWeather
import java.time.LocalTime

@Composable
fun WeatherSuccessScreen(weather: CityWeather) {
    val gradientBrush = Brush.verticalGradient(
        colors = if (LocalTime.now().isAfter(weather.weather.dayWeather.sunrise)) {
            listOf(Color(0xFF1d1c66), Color(0xFF7f6d8e))
        } else {
            listOf(Color(0xFF128cfe), Color(0xFF0074df))
        }
    )

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = gradientBrush)
                .padding(innerPadding)
                .padding(horizontal = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { TopMenuAction() }
        }
    }
}
