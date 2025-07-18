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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.jjinweather.R
import com.jin.jjinweather.feature.temperature.ui.ForecastTime
import com.jin.jjinweather.feature.weather.domain.model.HourlyForecast
import com.jin.jjinweather.feature.weather.domain.model.TemperatureSnapshot
import com.jin.jjinweather.feature.weather.domain.model.TemperatureSnapshotWithRain
import com.jin.jjinweather.ui.theme.DefaultTemperatureColor
import com.jin.jjinweather.ui.theme.TemperatureColors
import com.jin.jjinweather.ui.theme.TextColor40
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

@Composable
fun HourlyForecast(modifier: Modifier, backgroundColor: Color, timeZoneId: String, hourlyWeatherList: HourlyForecast) {
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
                        TemperatureColors[hourlyWeatherList[index].temperature.toInt()]
                            ?: DefaultTemperatureColor
                    Column(
                        modifier = Modifier.width(56.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        HourlyItem(timeZoneId, hourlyWeatherList[index])
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
            imageVector = Icons.Filled.AccessTimeFilled,
            contentDescription = stringResource(R.string.success_hourly_forecast_icon_desc),
            tint = Color.White,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = stringResource(R.string.success_hourly_forecast_title),
            fontSize = 14.sp,
            color = Color.LightGray,
            modifier = Modifier.padding(start = 4.dp)
        )
        Spacer(Modifier.weight(1f))
        Text(
            text = stringResource(R.string.success_hourly_forecast_view_vertically),
            fontSize = 14.sp,
            color = Color.LightGray
        )
        // FIXME : 세로 or 가로 toggle and saveSetting Value
        Switch(modifier = Modifier.scale(0.5f), checked = false, onCheckedChange = {})
    }
}

@Composable
private fun HourlyItem(timeZoneId: String, item: TemperatureSnapshotWithRain) {
    val amPmLabel = formatAmPmLabelAt(timeZoneId, item.timeStamp)
    val textAlpha = if (amPmLabel.isNotEmpty()) 1f else 0f
    val timeOrDayLabel = formatDayOrHourLabelAt(timeZoneId, item.timeStamp)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = amPmLabel,
            modifier = Modifier.alpha(textAlpha),
            color = Color.White,
            fontSize = 12.sp
        )
        ForecastTimeText(timeOrDayLabel)
        Image(
            painter = painterResource(id = item.icon.drawableRes),
            contentDescription = stringResource(R.string.success_hourly_forecast_temperature_icon_desc),
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = stringResource(R.string.success_temperature, item.temperature.toInt()),
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

@Composable
private fun ForecastTimeText(timeOrDayLabel: ForecastTime) {
    when (timeOrDayLabel) {
        is ForecastTime.RelativeDay -> {
            Box(
                modifier = Modifier.background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(12.dp)
                )
            ) {
                Text(
                    text = stringResource(timeOrDayLabel.labelRes),
                    color = TextColor40,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 2.dp)
                )
            }
        }

        is ForecastTime.Hour -> {
            Text(
                text = stringResource(R.string.success_hourly_forecast_hour, timeOrDayLabel.hour),
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun formatAmPmLabelAt(timeZoneId: String, timeStamp: Instant): String {
    val hour = timeStamp.atZone(ZoneId.of(timeZoneId)).hour
    return when (hour) {
        0, 6 -> stringResource(R.string.success_hourly_forecast_am)
        12, 18 -> stringResource(R.string.success_hourly_forecast_pm)
        else -> ""
    }
}

@Composable
private fun formatDayOrHourLabelAt(timeZoneId: String, timeStamp: Instant): ForecastTime {
    val now = LocalDate.now()
    val date = timeStamp.atZone(ZoneId.of(timeZoneId))
    val dayDiff = ChronoUnit.DAYS.between(now, date).toInt()
    val hour24 = date.hour
    val minute = date.minute
    val hour12 = if (hour24 > 12) hour24 - 12 else hour24

    return when {
        dayDiff == 2 && hour24 == 0 && minute == 0 -> ForecastTime.RelativeDay(R.string.success_day_after_tomorrow)
        dayDiff == 1 && hour24 == 0 -> ForecastTime.RelativeDay(R.string.success_tomorrow)
        else -> ForecastTime.Hour(hour12)
    }
}
