package com.jin.jjinweather.feature.outfitrecommend.ui.recommendcontent

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jin.jjinweather.R
import com.jin.jjinweather.feature.weather.domain.model.HourlyForecast
import com.jin.jjinweather.feature.weather.domain.model.TemperatureSnapshot
import com.jin.jjinweather.feature.weather.domain.model.WeatherIcon
import com.jin.jjinweather.ui.theme.JJinWeatherTheme
import java.time.Instant
import java.time.ZoneId

@Composable
fun OutfitRecommendScreen(
    imageUrl: String?,
    cityName: String,
    summary: String,
    forecast: HourlyForecast,
    feelsLikeTemperature: Int
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            OutfitHeader()
            CityNameAndWeatherSummary(cityName, summary)
            if (imageUrl != null) {
                OutfitSuccess(imageUrl)
            } else {
                OutfitError()
            }
            Text(
                text = stringResource(R.string.outfit_hourly_forecast_graph),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 10.dp)
            )
            HourlyForecastGraph(
                forecast.map { it.temperature.toInt() },
                forecast.map { it.timeStamp.atZone(ZoneId.systemDefault()).hour },
                feelsLikeTemperature,
                Modifier
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 10.dp)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun OutfitRecommendScreenPreview() {
    JJinWeatherTheme {
        val hourlyForecast: HourlyForecast = listOf(
            TemperatureSnapshot(Instant.ofEpochSecond(1747893600), WeatherIcon.SCATTERED_CLOUDS, 28.87),
            TemperatureSnapshot(Instant.ofEpochSecond(1747897200), WeatherIcon.SCATTERED_CLOUDS, 28.31),
            TemperatureSnapshot(Instant.ofEpochSecond(1747900800), WeatherIcon.SCATTERED_CLOUDS, 27.83),
            TemperatureSnapshot(Instant.ofEpochSecond(1747904400), WeatherIcon.SCATTERED_CLOUDS, 26.55),
            TemperatureSnapshot(Instant.ofEpochSecond(1747908000), WeatherIcon.SCATTERED_CLOUDS, 24.57),
            TemperatureSnapshot(Instant.ofEpochSecond(1747911600), WeatherIcon.RAIN_NIGHT, 22.25),
            TemperatureSnapshot(Instant.ofEpochSecond(1747915200), WeatherIcon.RAIN_NIGHT, 19.89),
            TemperatureSnapshot(Instant.ofEpochSecond(1747918800), WeatherIcon.RAIN_NIGHT, 20.37),
            TemperatureSnapshot(Instant.ofEpochSecond(1747922400), WeatherIcon.RAIN_NIGHT, 19.61),
            TemperatureSnapshot(Instant.ofEpochSecond(1747926000), WeatherIcon.SCATTERED_CLOUDS, 18.92),
            TemperatureSnapshot(Instant.ofEpochSecond(1747929600), WeatherIcon.SCATTERED_CLOUDS, 18.48),
            TemperatureSnapshot(Instant.ofEpochSecond(1747933200), WeatherIcon.SCATTERED_CLOUDS, 18.35),
            TemperatureSnapshot(Instant.ofEpochSecond(1747936800), WeatherIcon.SCATTERED_CLOUDS, 18.79),
            TemperatureSnapshot(Instant.ofEpochSecond(1747940400), WeatherIcon.SCATTERED_CLOUDS, 18.78),
            TemperatureSnapshot(Instant.ofEpochSecond(1747944000), WeatherIcon.SCATTERED_CLOUDS, 18.45),
            TemperatureSnapshot(Instant.ofEpochSecond(1747947600), WeatherIcon.SCATTERED_CLOUDS, 18.63),
            TemperatureSnapshot(Instant.ofEpochSecond(1747951200), WeatherIcon.SCATTERED_CLOUDS, 18.27),
            TemperatureSnapshot(Instant.ofEpochSecond(1747954800), WeatherIcon.SCATTERED_CLOUDS, 18.21),
            TemperatureSnapshot(Instant.ofEpochSecond(1747958400), WeatherIcon.SCATTERED_CLOUDS, 19.03),
            TemperatureSnapshot(Instant.ofEpochSecond(1747962000), WeatherIcon.SCATTERED_CLOUDS, 17.01),
            TemperatureSnapshot(Instant.ofEpochSecond(1747965600), WeatherIcon.SCATTERED_CLOUDS, 17.18),
            TemperatureSnapshot(Instant.ofEpochSecond(1747969200), WeatherIcon.SCATTERED_CLOUDS, 18.07),
            TemperatureSnapshot(Instant.ofEpochSecond(1747972800), WeatherIcon.SCATTERED_CLOUDS, 17.92),
            TemperatureSnapshot(Instant.ofEpochSecond(1747976400), WeatherIcon.SCATTERED_CLOUDS, 17.78),
            TemperatureSnapshot(Instant.ofEpochSecond(1747980000), WeatherIcon.SCATTERED_CLOUDS, 18.54),
            TemperatureSnapshot(Instant.ofEpochSecond(1747983600), WeatherIcon.SCATTERED_CLOUDS, 18.04),
            TemperatureSnapshot(Instant.ofEpochSecond(1747987200), WeatherIcon.SCATTERED_CLOUDS, 17.59),
            TemperatureSnapshot(Instant.ofEpochSecond(1747990800), WeatherIcon.RAIN_DAY, 15.87),
            TemperatureSnapshot(Instant.ofEpochSecond(1747994400), WeatherIcon.SCATTERED_CLOUDS, 15.45),
            TemperatureSnapshot(Instant.ofEpochSecond(1747998000), WeatherIcon.SCATTERED_CLOUDS, 15.76),
            TemperatureSnapshot(Instant.ofEpochSecond(1748001600), WeatherIcon.RAIN_NIGHT, 14.91),
            TemperatureSnapshot(Instant.ofEpochSecond(1748005200), WeatherIcon.RAIN_NIGHT, 14.12),
            TemperatureSnapshot(Instant.ofEpochSecond(1748008800), WeatherIcon.RAIN_NIGHT, 13.93),
            TemperatureSnapshot(Instant.ofEpochSecond(1748012400), WeatherIcon.SCATTERED_CLOUDS, 14.16),
            TemperatureSnapshot(Instant.ofEpochSecond(1748016000), WeatherIcon.RAIN_NIGHT, 13.86),
            TemperatureSnapshot(Instant.ofEpochSecond(1748019600), WeatherIcon.SCATTERED_CLOUDS, 13.90),
            TemperatureSnapshot(Instant.ofEpochSecond(1748023200), WeatherIcon.SCATTERED_CLOUDS, 13.91),
            TemperatureSnapshot(Instant.ofEpochSecond(1748026800), WeatherIcon.SCATTERED_CLOUDS, 13.68),
            TemperatureSnapshot(Instant.ofEpochSecond(1748030400), WeatherIcon.SCATTERED_CLOUDS, 13.75),
            TemperatureSnapshot(Instant.ofEpochSecond(1748034000), WeatherIcon.SCATTERED_CLOUDS, 13.99),
            TemperatureSnapshot(Instant.ofEpochSecond(1748037600), WeatherIcon.SCATTERED_CLOUDS, 14.60),
            TemperatureSnapshot(Instant.ofEpochSecond(1748041200), WeatherIcon.SCATTERED_CLOUDS, 15.93),
            TemperatureSnapshot(Instant.ofEpochSecond(1748044800), WeatherIcon.SCATTERED_CLOUDS, 16.69),
            TemperatureSnapshot(Instant.ofEpochSecond(1748048400), WeatherIcon.SCATTERED_CLOUDS, 18.61),
            TemperatureSnapshot(Instant.ofEpochSecond(1748052000), WeatherIcon.SCATTERED_CLOUDS, 20.46),
            TemperatureSnapshot(Instant.ofEpochSecond(1748055600), WeatherIcon.SCATTERED_CLOUDS, 22.13),
            TemperatureSnapshot(Instant.ofEpochSecond(1748059200), WeatherIcon.SCATTERED_CLOUDS, 23.29),
            TemperatureSnapshot(Instant.ofEpochSecond(1748062800), WeatherIcon.SCATTERED_CLOUDS, 22.84),
        )
        OutfitRecommendScreen(null, "서초구 방배동", "바람이 약간 부는 날이에요.", hourlyForecast, 21)
    }
}
