package com.jin.jjinweather.feature.temperature.ui.weathercontent.success

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTimeFilled
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.jjinweather.feature.weather.domain.model.HourlyForecast
import com.jin.jjinweather.feature.weather.domain.model.TemperatureSnapshot
import com.jin.jjinweather.ui.theme.*
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

@Composable
fun HourlyForecast(modifier: Modifier, backgroundColor: Color, hourlyWeatherList: HourlyForecast) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
    ) {
        Column {
            HourlyHeader()
            HorizontalDivider(thickness = 1.dp)
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(hourlyWeatherList.size) { index ->
                    val temperatureColor =
                        mapTemperatureToColor(hourlyWeatherList[index].temperature.toInt())
                    Column(
                        modifier = Modifier
                            .width(56.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        HourlyItem(hourlyWeatherList[index])
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .background(temperatureColor)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HourlyHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Filled.AccessTimeFilled,
            contentDescription = "",
            tint = Color.White,
            modifier = Modifier
                .size(16.dp)
        )
        Text("시간별 예보", fontSize = 14.sp, color = Color.LightGray, modifier = Modifier.padding(start = 4.dp))
        Spacer(Modifier.weight(1f))
        Text("세로로 보기", fontSize = 14.sp, color = Color.LightGray)
        // FIXME : 세로 or 가로 toggle and saveSetting Value
        Switch(modifier = Modifier.scale(0.5f), checked = false, onCheckedChange = {})
    }
}

@Composable
private fun HourlyItem(item: TemperatureSnapshot) {
    val amPmLabel = formatAmPmLabelAt(item.timeStamp)
    val textAlpha = if (amPmLabel.isNotEmpty()) 1f else 0f
    val timeOrDayLabel = formatDayOrHourLabelAt(item.timeStamp)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = amPmLabel, modifier = Modifier.alpha(textAlpha), color = Color.White, fontSize = 12.sp
        )
        if (timeOrDayLabel == "모레") {
            Box(modifier = Modifier.background(color = Color.LightGray, shape = RoundedCornerShape(12.dp))) {
                Text(
                    text = timeOrDayLabel, color = Color(0xFF444444), fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 2.dp)
                )
            }
        } else {
            Text(
                text = timeOrDayLabel, color = Color.White, fontSize = 14.sp
            )
        }
        Image(
            painter = painterResource(id = item.icon.drawableRes),
            contentDescription = "hourly temp icon",
            modifier = Modifier.size(32.dp)
        )
        Text(
            "${item.temperature.toInt()}°", color = Color.White, fontSize = 14.sp,
            fontWeight = FontWeight.Bold, modifier = Modifier
                .padding(top = 10.dp)
        )
    }
}

private fun formatAmPmLabelAt(timeStamp: Instant): String {
    val hour = timeStamp.atZone(ZoneId.systemDefault()).hour
    return when (hour) {
        0, 6 -> "오전"
        12, 18 -> "오후"
        else -> ""
    }
}
private fun formatDayOrHourLabelAt(timeStamp: Instant): String {
    val now = LocalDate.now()
    val date = timeStamp.atZone(ZoneId.systemDefault())
    val dayDiff = ChronoUnit.DAYS.between(now, date).toInt()
    val hour24 = date.hour
    val minute = date.minute
    val hour12 = if (hour24 % 12 == 0) 12 else hour24 % 12

    return if (dayDiff == 2 && hour24 == 0 && minute == 0) {
        "모레"
    } else {
        "${hour12}시"
    }
}


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
