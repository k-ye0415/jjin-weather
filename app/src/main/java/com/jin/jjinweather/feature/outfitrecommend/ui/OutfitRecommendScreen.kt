package com.jin.jjinweather.feature.outfitrecommend.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jin.jjinweather.R
import com.jin.jjinweather.feature.temperature.ui.ForecastTime
import com.jin.jjinweather.feature.weather.domain.model.HourlyForecast
import com.jin.jjinweather.feature.weather.domain.model.TemperatureSnapshot
import com.jin.jjinweather.feature.weather.domain.model.WeatherIcon
import com.jin.jjinweather.ui.theme.ButtonColor
import com.jin.jjinweather.ui.theme.DefaultTemperatureColor
import com.jin.jjinweather.ui.theme.JJinWeatherTheme
import com.jin.jjinweather.ui.theme.PointColor
import com.jin.jjinweather.ui.theme.TemperatureColors
import java.time.Instant
import java.time.ZoneId
import kotlin.math.ceil
import kotlin.math.floor

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
            CityNameAndSummary(cityName, summary)
            if (imageUrl != null) {
                OutfitSuccess(imageUrl)
            } else {
                OutfitError()
            }
            Text(
                "시간별 온도 그래프",
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
private fun OutfitHeader() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        IconButton({}) {
            Icon(
                imageVector = Icons.Outlined.ArrowBackIosNew,
                contentDescription = "뒤로가기",
                tint = Color.Black
            )
        }
        Text(
            text = "오늘 뭐 입지?",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}

@Composable
private fun CityNameAndSummary(cityName: String, summary: String) {
    Column(
        modifier = Modifier.padding(
            horizontal = 20.dp,
            vertical = 10.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.NearMe,
                contentDescription = "현재위치",
                tint = Color.Black,
                modifier = Modifier.size(18.dp)
            )
            Text(cityName)
        }
        Text(
            text = summary,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}

@Composable
private fun HourlyForecastGraph(
    temperatureList: List<Int>,
    hourlyList: List<Int>,
    feelsLikeTemperature: Int,
    modifier: Modifier
) {
    val lowestTemperature = temperatureList.minOfOrNull { it } ?: 0
    val highestTemperature = temperatureList.maxOfOrNull { it } ?: 0
    val graphMinTemperature = findGraphMinMaxTemperature(lowestTemperature)
    val graphMaxTemperature = findGraphMinMaxTemperature(highestTemperature)

    val temperatureLabels = findGraphTemperatureLabels(lowestTemperature, graphMinTemperature, graphMaxTemperature)
    val hourLabels = findHourLabels(hourlyList)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .drawBehind {
                val strokeWidth = 2.dp.toPx()
                val cornerRadius = 8.dp.toPx()
                drawRoundRect(
                    color = Color.DarkGray, // 또는 brush로 그라데이션
                    style = Stroke(
                        width = strokeWidth,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f)) // 점선 패턴
                    ),
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                )
            }
    ) {
        TemperatureYAxisLabels(temperatureLabels)
        VerticalDivider(thickness = 1.dp, modifier = Modifier.padding(bottom = 32.dp))
        Box {
            HourXAxisLabels(hourLabels, temperatureList, graphMinTemperature, graphMaxTemperature, feelsLikeTemperature)
            TemperatureGraphDescription(graphMaxTemperature, lowestTemperature)
        }
    }
}

@Composable
private fun TemperatureGraphDescription(maxTemperature: Int, minTemperature: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, end = 8.dp),
        horizontalAlignment = Alignment.End
    ) {
        DescriptionItem(TemperatureColors[maxTemperature] ?: DefaultTemperatureColor, "최고 온도", RectangleShape)
        DescriptionItem(TemperatureColors[minTemperature] ?: DefaultTemperatureColor, "최저 온도", RectangleShape)
        DescriptionItem(PointColor, "체감 온도", CircleShape)
    }
}

@Composable
private fun DescriptionItem(color: Color, title: String, shape: Shape) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .clip(shape)
                .size(12.dp)
                .background(color)
        )
        Text(
            text = title,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 2.dp),
            lineHeight = 1.5.em
        )
    }
}

@Composable
private fun TemperatureYAxisLabels(temperatureLabels: List<Int>) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(
                top = 20.dp,
                start = 10.dp, end = 4.dp
            ),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End
    ) {
        for (temp in temperatureLabels) {
            Text(
                text = stringResource(R.string.success_temperature, temp),
                fontSize = 12.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(
                    bottom = if (temp == temperatureLabels.last()) 32.dp else 0.dp
                )
            )
        }
    }
}

@Composable
private fun HourXAxisLabels(
    hourLabels: List<ForecastTime.Hour>,
    temperatureList: List<Int>,
    absoluteMinTemperature: Int,
    absoluteMaxTemperature: Int,
    feelsLikeTemperature: Int
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(hourLabels.size) { i ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier
                    .width(32.dp)
                    .fillMaxHeight()
            ) {
                // 막대 그래프
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    val temp = temperatureList[i]

                    // 아이템의 위치 계산
                    val barCenterX = size.width / 2f
                    val graphMinHeight = 8.dp.toPx() // 최소 높이 설정(최고와 최저의 차이가 작으면 UI 에러 발생)
                    val tempRange = (absoluteMaxTemperature - absoluteMinTemperature).takeIf { it != 0 } ?: 1
                    val graphHeight = ((temp - absoluteMinTemperature).toFloat() / tempRange) * size.height
                    val barHeight = maxOf(graphMinHeight, graphHeight)

                    // 체감온도의 위치 계산
                    val feelsLikeHeight =
                        ((feelsLikeTemperature - absoluteMinTemperature).toFloat() / tempRange) * size.height

                    // 그래프 색상
                    val topColor = TemperatureColors[temp] ?: DefaultTemperatureColor
                    val bottomColor = TemperatureColors[absoluteMinTemperature] ?: DefaultTemperatureColor
                    val brush = Brush.verticalGradient(
                        colors = listOf(topColor, bottomColor),
                        startY = size.height - barHeight,
                        endY = size.height
                    )

                    drawLine(
                        brush = brush,
                        start = Offset(barCenterX, size.height),
                        end = Offset(barCenterX, size.height - barHeight),
                        strokeWidth = 8.dp.toPx()
                    )

                    // 현재 시각의 체감 온도를 표시.
                    // FIXME : 모든 시간의 체감온도 표시?
                    if (i == 0) {
                        drawCircle(
                            color = PointColor,
                            radius = 6.dp.toPx(),
                            center = Offset(barCenterX, size.height - feelsLikeHeight)
                        )
                    }
                }
                HorizontalDivider()
                // 시간 라벨
                Box(modifier = Modifier.height(32.dp)) {
                    Text(
                        text = stringResource(R.string.success_hourly_forecast_hour, hourLabels[i].hour),
                        fontSize = 12.sp,
                        color = Color.DarkGray,
                    )
                }
            }
        }
    }
}

private fun findGraphMinMaxTemperature(temperature: Int): Int {
    return if (temperature < 0) {
        floor(temperature / 5.0).toInt() * 5
    } else {
        ceil(temperature / 5.0).toInt() * 5
    }
}

private fun findGraphTemperatureLabels(
    lowestTemperature: Int,
    graphMinTemperature: Int,
    graphMaxTemperature: Int
): List<Int> {
    val temperatureLabelSet = (graphMinTemperature..graphMaxTemperature step 5).toMutableSet()
    temperatureLabelSet.add(lowestTemperature)
    return temperatureLabelSet.sortedDescending()
}

private fun findHourLabels(hourlyList: List<Int>): List<ForecastTime.Hour> {
    val hours = hourlyList.indices.map { hourlyList[it] }
    return hours.map { hour24 ->
        val hour12 = if (hour24 > 12) hour24 - 12 else hour24
        ForecastTime.Hour(hour12)
    }
}


@Composable
private fun OutfitSuccess(imageUrl: String) {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 20.dp),
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = stringResource(R.string.outfit_success_img_desc)
        )
        Box(
            modifier = Modifier
                .clickable { }
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
                .size(32.dp)
                .padding(2.dp)
        ) {
            // FIXME : 다른 이미지 전환?
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Outlined.Repeat,
                contentDescription = stringResource(R.string.outfit_success_switch_icon_desc),
                tint = Color.White
            )
        }
    }
}

@Composable
private fun OutfitError() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 20.dp),
            painter = painterResource(R.drawable.img_outfit_error),
            contentDescription = stringResource(R.string.outfit_error_img_desc)
        )
        Text(
            text = stringResource(R.string.outfit_error),
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 20.dp)
        )
        //FIXME : 재요청?
        Box(
            modifier = Modifier
                .clickable { }
                .clip(RoundedCornerShape(8.dp))
                .background(ButtonColor)
                .padding(horizontal = 4.dp),
        ) {
            Text(
                text = stringResource(R.string.outfit_error_button_retry),
                fontSize = 10.sp,
                color = Color.White
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
