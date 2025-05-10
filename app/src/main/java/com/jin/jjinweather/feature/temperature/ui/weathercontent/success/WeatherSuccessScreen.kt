package com.jin.jjinweather.feature.temperature.ui.weathercontent.success

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
import com.jin.jjinweather.feature.weather.domain.model.CityWeather
import com.jin.jjinweather.ui.theme.SuccessBackgroundBottomDayColor
import com.jin.jjinweather.ui.theme.SuccessBackgroundBottomNightColor
import com.jin.jjinweather.ui.theme.SuccessBackgroundTopDayColor
import com.jin.jjinweather.ui.theme.SuccessBackgroundTopNightColor
import com.jin.jjinweather.ui.theme.SuccessCardBackgroundDayColor
import com.jin.jjinweather.ui.theme.SuccessCardBackgroundNightColor
import com.jin.jjinweather.ui.theme.Temperature10
import com.jin.jjinweather.ui.theme.Temperature11
import com.jin.jjinweather.ui.theme.Temperature12
import com.jin.jjinweather.ui.theme.Temperature13
import com.jin.jjinweather.ui.theme.Temperature14
import com.jin.jjinweather.ui.theme.Temperature15
import com.jin.jjinweather.ui.theme.Temperature16
import com.jin.jjinweather.ui.theme.Temperature17
import com.jin.jjinweather.ui.theme.Temperature18
import com.jin.jjinweather.ui.theme.Temperature19
import com.jin.jjinweather.ui.theme.Temperature20
import com.jin.jjinweather.ui.theme.Temperature21
import com.jin.jjinweather.ui.theme.Temperature22
import com.jin.jjinweather.ui.theme.Temperature23
import com.jin.jjinweather.ui.theme.Temperature24
import com.jin.jjinweather.ui.theme.Temperature25
import com.jin.jjinweather.ui.theme.Temperature9
import java.time.LocalTime

@Composable
fun WeatherSuccessScreen(weather: CityWeather) {
    val isAfterSunset = LocalTime.now().isAfter(weather.weather.dayWeather.sunset)
    val backgroundGradientBrush = generateBackgroundColor(isAfterSunset)
    val cardBackgroundColor = generatedCardBackgroundColor(isAfterSunset)

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
            item {
                DetailWeather(
                    modifier = Modifier.padding(bottom = 20.dp),
                    backgroundColor = cardBackgroundColor,
                    sunrise = weather.weather.dayWeather.sunrise,
                    sunset = weather.weather.dayWeather.sunset,
                    moonPhase = weather.weather.dayWeather.moonPhase
                )
            }
        }
    }
}

private fun generateBackgroundColor(isAfterSunset: Boolean) = Brush.verticalGradient(
    colors = if (isAfterSunset) {
        listOf(SuccessBackgroundTopNightColor, SuccessBackgroundBottomNightColor)
    } else {
        listOf(SuccessBackgroundTopDayColor, SuccessBackgroundBottomDayColor)
    }
)

private fun generatedCardBackgroundColor(isAfterSunset: Boolean) =
    if (isAfterSunset) SuccessCardBackgroundNightColor else SuccessCardBackgroundDayColor

fun mapTemperatureToColor(temperature: Int): Color {
    return when (temperature) {
        9 -> Temperature9
        10 -> Temperature10
        11 -> Temperature11
        12 -> Temperature12
        13 -> Temperature13
        14 -> Temperature14
        15 -> Temperature15
        16 -> Temperature16
        17 -> Temperature17
        18 -> Temperature18
        19 -> Temperature19
        20 -> Temperature20
        21 -> Temperature21
        22 -> Temperature22
        23 -> Temperature23
        24 -> Temperature24
        25 -> Temperature25
        else -> Temperature10
    }
}
