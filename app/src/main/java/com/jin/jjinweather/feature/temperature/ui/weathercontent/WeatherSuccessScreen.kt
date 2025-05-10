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
import com.jin.jjinweather.feature.temperature.ui.weathercontent.success.CurrentWeatherOverview
import com.jin.jjinweather.feature.temperature.ui.weathercontent.success.DailyForecast
import com.jin.jjinweather.feature.temperature.ui.weathercontent.success.HourlyForecast
import com.jin.jjinweather.feature.temperature.ui.weathercontent.success.TopMenuAction
import com.jin.jjinweather.feature.temperature.ui.weathercontent.success.YesterdayWeatherOutfit
import com.jin.jjinweather.feature.weather.domain.model.CityWeather
import java.time.LocalTime

@Composable
fun WeatherSuccessScreen(weather: CityWeather) {
    val isAfterSunset = LocalTime.now().isAfter(weather.weather.dayWeather.sunset)
    val backgroundGradientBrush = Brush.verticalGradient(
        colors = if (isAfterSunset) {
            listOf(Color(0xFF1d1c66), Color(0xFF7f6d8e))
        } else {
            listOf(Color(0xFF128cfe), Color(0xFF0074df))
        }
    )
    val cardBackgroundColor = if (isAfterSunset) Color(0x501d1c66) else Color(0x50035198)

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = backgroundGradientBrush)
                .padding(innerPadding)
                .padding(horizontal = 18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { TopMenuAction() }
            item {
                CurrentWeatherOverview(
                    cityName = weather.cityName,
                    currentTemperature = weather.weather.dayWeather.temperature.toInt(),
                    currentWeatherIconRes = weather.weather.dayWeather.icon.drawableRes,
                    todayMinTemperature = weather.weather.dayWeather.temperatureRange.min.toInt(),
                    todayMaxTemperature = weather.weather.dayWeather.temperatureRange.max.toInt(),
                    yesterdayTemperature = weather.weather.yesterdayWeather.temperature.toInt()
                )
            }
            item {
                YesterdayWeatherOutfit(
                    backgroundColor = cardBackgroundColor,
                    yesterdayTemperature = weather.weather.yesterdayWeather.temperature.toInt()
                )
            }
            item {
                HourlyForecast(
                    modifier = Modifier.padding(bottom = 10.dp),
                    backgroundColor = cardBackgroundColor,
                    hourlyWeatherList = weather.weather.forecast.hourly
                )
            }
            item {
                DailyForecast(
                    modifier = Modifier.padding(bottom = 10.dp),
                    backgroundColor = cardBackgroundColor,
                    dailyWeatherList = weather.weather.forecast.daily
                )
            }
        }
    }
}
