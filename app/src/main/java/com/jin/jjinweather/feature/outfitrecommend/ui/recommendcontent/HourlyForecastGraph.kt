package com.jin.jjinweather.feature.outfitrecommend.ui.recommendcontent

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.jin.jjinweather.R
import com.jin.jjinweather.feature.temperature.ui.ForecastTime
import com.jin.jjinweather.ui.theme.DefaultTemperatureColor
import com.jin.jjinweather.ui.theme.PointColor
import com.jin.jjinweather.ui.theme.TemperatureColors
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun HourlyForecastGraph(
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
                    color = Color.DarkGray,
                    style = Stroke(
                        width = strokeWidth,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f)) // 점선 패턴
                    ),
                    cornerRadius = CornerRadius(cornerRadius, cornerRadius)
                )
            }
    ) {
        // 최저~최고 온도 표시
        TemperatureYAxisLabels(temperatureLabels)
        VerticalDivider(thickness = 1.dp, modifier = Modifier.padding(bottom = 32.dp))

        // 시간별 최저/최고 막대 그래프 표시
        Box {
            HourXAxisLabels(hourLabels, temperatureList, graphMinTemperature, graphMaxTemperature, feelsLikeTemperature)
            TemperatureGraphDescription(graphMaxTemperature, lowestTemperature)
        }
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
