package com.jin.jjinweather.feature.temperature.ui.weathercontent.success

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.jin.jjinweather.R
import com.jin.jjinweather.ui.theme.DetailWeatherIconBackgroundColor
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DetailWeather(
    modifier: Modifier,
    backgroundColor: Color,
    sunrise: LocalTime,
    sunset: LocalTime,
    moonPhase: Double
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
    ) {
        Column {
            DetailWeatherHeader()
            HorizontalDivider(thickness = 1.dp)
            SunProgressIndicator(sunrise, sunset, LocalTime.now())
            HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp), thickness = 1.dp)
            MoreWeather(moonPhase)
        }
    }
}

@Composable
private fun DetailWeatherHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Outlined.Apps,
            contentDescription = stringResource(R.string.success_detail_weather_icon_desc),
            tint = Color.White,
            modifier = Modifier
                .size(16.dp)
        )
        Text(
            text = stringResource(R.string.success_detail_weather_title),
            fontSize = 14.sp,
            color = Color.LightGray,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Composable
private fun SunProgressIndicator(
    sunrise: LocalTime,
    sunset: LocalTime,
    now: LocalTime
) {
    val percent = calculateSunProgress(sunrise, sunset, now)
    val animatedPercent = remember { Animatable(0f) }

    LaunchedEffect(percent) {
        animatedPercent.animateTo(
            targetValue = percent,
            animationSpec = tween(durationMillis = 1000, easing = LinearEasing),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 100.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val stroke = 20f

                    // 직경 계산 후 아크 크기 지정
                    val arcDiameter = size.minDimension * 2f
                    val arcSize = Size(arcDiameter, arcDiameter)

                    // 아크의 중심을 캔버스 좌상단의 기준으로 중앙에 정렬하기 위한 계산.
                    // x : 좌우 중앙
                    // y : 아크의 하단이 캔버스 하단과 닿도록 bottom 계산
                    val arcTopLeft = Offset(
                        x = (size.width - arcSize.width) / 2f,
                        y = (size.height - arcSize.height)
                    )

                    // 배경 아크
                    drawArc(
                        color = Color.LightGray,
                        startAngle = 180f,
                        sweepAngle = 180f,
                        useCenter = false,
                        topLeft = arcTopLeft,
                        size = arcSize,
                        style = Stroke(width = stroke, cap = StrokeCap.Round)
                    )

                    // 진행률 아크
                    drawArc(
                        color = Color.White,
                        startAngle = 180f,
                        sweepAngle = 180f * animatedPercent.value,
                        useCenter = false,
                        topLeft = arcTopLeft,
                        size = arcSize,
                        style = Stroke(width = stroke, cap = StrokeCap.Round)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val formatter = DateTimeFormatter.ofPattern("a h:mm", Locale.getDefault())
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = stringResource(R.string.success_detail_weather_sunrise),
                            color = Color.LightGray,
                            fontSize = 8.sp,
                            lineHeight = 1.5.em
                        )
                        Text(text = sunrise.format(formatter), color = Color.White, fontSize = 12.sp)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            stringResource(R.string.success_detail_weather_sunset),
                            color = Color.LightGray,
                            fontSize = 8.sp,
                            lineHeight = 1.5.em
                        )
                        Text(sunset.format(formatter), color = Color.White, fontSize = 12.sp)
                    }
                }
            }
        }
        // FIXME : 일몰 이후 다음 날의 일출 시간 정보 필요.
        val sunDiff = formatRemainingSunEventLabel(now, sunrise, sunset)
        Text(text = sunDiff, color = Color.White, fontSize = 12.sp)
    }
}

private fun calculateSunProgress(
    sunrise: LocalTime,
    sunset: LocalTime,
    now: LocalTime
): Float {
    // 일몰이 일출보다 큰 경우에 대한 방어 코드.
    if (sunset <= sunrise) return 0f

    return when {
        now <= sunrise -> 0f
        now >= sunset -> 1f
        else -> {
            val totalSeconds = Duration.between(sunrise, sunset).seconds
            val elapsedSeconds = Duration.between(sunrise, now).seconds
            (elapsedSeconds.toFloat() / totalSeconds.toFloat()).coerceIn(0f, 1f)
        }
    }
}

@Composable
private fun formatRemainingSunEventLabel(
    now: LocalTime,
    nextSunrise: LocalTime,
    sunset: LocalTime
): String {
    return if (now.isAfter(sunset)) {
        // 다음 날 일출까지 남은 시간
        val duration = Duration.between(now, nextSunrise)
        val hours = duration.toHours()
        val minutes = duration.minusHours(hours).toMinutes()
        stringResource(R.string.success_detail_weather_start_sunrise, hours, minutes)
    } else {
        // 오늘 일몰까지 남은 시간
        val duration = Duration.between(now, sunset)
        val hours = duration.toHours()
        val minutes = duration.minusHours(hours).toMinutes()
        stringResource(R.string.success_detail_weather_start_sunset, hours, minutes)
    }
}

@Composable
private fun MoreWeather(moonPhase: Double) {
    Row(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(DetailWeatherIconBackgroundColor, CircleShape)
                .size(30.dp),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(R.drawable.ic_main_few_clouds_night),
                contentDescription = stringResource(R.string.success_detail_weather_weather_icon_desc),
                tint = Color.Unspecified
            )
        }
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = stringResource(R.string.success_detail_weather_weather),
            color = Color.White,
            fontSize = 14.sp
        )
        Spacer(Modifier.weight(1f))
        // FIXME : 날씨에 대한 설명글 필요
        Text("대체로 맑음", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
    Row(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val (moonIcon, moonLabel) = mapMoonPhaseToMoonIconAndLabel(moonPhase)
        Box(
            modifier = Modifier
                .background(DetailWeatherIconBackgroundColor, CircleShape)
                .size(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(moonIcon)
        }
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = stringResource(R.string.success_detail_weather_moon),
            color = Color.White,
            fontSize = 14.sp
        )
        Spacer(Modifier.weight(1f))
        Text(moonLabel, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

// FIXME : [WeatherIcon] class 처럼 처리 필요.
private fun mapMoonPhaseToMoonIconAndLabel(value: Double): Pair<String, String> = when {
    value <= 0.03 || value == 1.0 -> Pair("🌑", "새로운 달")
    value in 0.04..0.24 -> Pair("🌒", "초승달")
    value == 0.25 -> Pair("🌓", "반달")
    value in 0.26..0.49 -> Pair("🌔", "보름달 전")
    value == 0.5 -> Pair("🌕", "보름달")
    value in 0.51..0.74 -> Pair("🌖", "보름달 후")
    value == 0.75 -> Pair("🌗", "반달")
    value in 0.76..0.99 -> Pair("🌘", "초승달")
    else -> Pair("🌑", "알 수 없음")
}
