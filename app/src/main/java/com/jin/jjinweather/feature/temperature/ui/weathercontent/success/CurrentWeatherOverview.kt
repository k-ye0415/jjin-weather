package com.jin.jjinweather.feature.temperature.ui.weathercontent.success

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.jjinweather.R

@Composable
fun CurrentWeatherOverview(
    cityName: String,
    currentTemperature: Int,
    currentWeatherIconRes: Int,
    todayMinTemperature: Int,
    todayMaxTemperature: Int,
    yesterdayTemperature: Int
) {
    val infiniteTransition = rememberInfiniteTransition(label = "rocking")
    val rotation by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rockingAngle"
    )

    val temperatureDescription = describeTemperatureComparedToYesterday(currentTemperature, yesterdayTemperature)
    Column(
        modifier = Modifier.padding(top = 50.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = cityName,
            color = Color.White,
            fontSize = 20.sp
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = currentWeatherIconRes),
                contentDescription = stringResource(R.string.success_current_temperature_icon_desc),
                modifier = Modifier
                    .size(64.dp)
                    .rotate(rotation)
            )
            Text(
                stringResource(R.string.success_temperature, currentTemperature),
                color = Color.White,
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = temperatureDescription,
            color = Color.White,
            fontSize = 20.sp
        )
        Row(modifier = Modifier.padding(bottom = 60.dp)) {
            Text(
                stringResource(R.string.success_current_min_temperature, todayMinTemperature),
                modifier = Modifier.padding(end = 8.dp),
                fontSize = 18.sp,
                color = Color.LightGray
            )
            Text(
                stringResource(R.string.success_current_max_temperature, todayMaxTemperature),
                fontSize = 18.sp,
                color = Color.LightGray
            )
        }

        // FIXME : need pager and indicator
        Icon(
            imageVector = Icons.Filled.NearMe,
            contentDescription = "현재위치",
            tint = Color.White,
            modifier = Modifier.size(12.dp)
        )
    }
}

@Composable
private fun describeTemperatureComparedToYesterday(currentTemperature: Int, yesterdayTemperature: Int): String {
    val difference = currentTemperature - yesterdayTemperature
    return when {
        difference > 0 -> stringResource(R.string.success_compared_high, difference)
        difference < 0 -> stringResource(R.string.success_compared_low, -difference)
        else -> stringResource(R.string.success_compared_same)
    }
}
