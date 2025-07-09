package com.jin.jjinweather.feature.outfitrecommend.ui.recommendcontent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jin.jjinweather.R
import com.jin.jjinweather.feature.weather.domain.model.HourlyForecast
import java.time.ZoneId

@Composable
fun OutfitRecommendScreen(
    outfitTypes: List<String>?,
    cityName: String,
    summary: String,
    timeZoneId: String,
    forecast: HourlyForecast,
    feelsLikeTemperature: Int,
    onNavigateToTemperature: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            OutfitHeader(onNavigateToTemperature)
            CityNameAndWeatherSummary(cityName, summary)
            if (outfitTypes == null) {
                OutfitError()
            } else if (outfitTypes.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                OutfitSuccess(outfitTypes)
            }
            Text(
                text = stringResource(R.string.outfit_hourly_forecast_graph),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 10.dp)
            )
            HourlyForecastGraph(
                temperatureList = forecast.map { it.temperature.toInt() },
                hourlyList = forecast.map { it.timeStamp.atZone(ZoneId.of(timeZoneId)).hour },
                feelsLikeTemperature = feelsLikeTemperature,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 10.dp)
            )
        }
    }
}
