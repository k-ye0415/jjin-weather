package com.jin.jjinweather.feature.temperature.ui.weathercontent.success

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.jjinweather.R


@Composable
fun YesterdayWeatherOutfit(backgroundColor: Color, yesterdayTemperature: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        // FIXME : need yesterday weather icon resource
        WeatherSummaryCard(
            backgroundColor = backgroundColor,
            iconPainter = painterResource(R.drawable.ic_main_mist),
            title = stringResource(R.string.success_yesterday),
            subtitle = stringResource(R.string.success_temperature, yesterdayTemperature),
            iconDescription = stringResource(R.string.success_yesterday_icon_desc)
        )
        WeatherSummaryCard(
            backgroundColor = backgroundColor,
            iconPainter = painterResource(R.drawable.ic_clothes),
            title = stringResource(R.string.success_today_outfit_title),
            subtitle = stringResource(R.string.success_today_outfit_sub_title),
            iconDescription = stringResource(R.string.success_today_outfit_icon_desc)
        )
    }
}

// FIXME : Outfit 개발 시 수정 필요
@Composable
private fun WeatherSummaryCard(
    backgroundColor: Color,
    iconPainter: Painter,
    title: String,
    subtitle: String,
    iconDescription: String
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = iconPainter,
            contentDescription = iconDescription,
            tint = Color.Unspecified,
            modifier = Modifier
                .padding(end = 4.dp)
                .size(24.dp)
        )
        Column(
            modifier = Modifier.padding(end = 8.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = title,
                fontSize = 12.sp,
                color = Color.LightGray,
                lineHeight = 12.sp,
            )
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                lineHeight = 16.sp
            )
        }
        // FIXME : 옷 추천 진입 부분 필요
        Icon(
            imageVector = Icons.Outlined.ArrowForwardIos,
            contentDescription = null,
            modifier = Modifier.size(12.dp),
            tint = Color.LightGray
        )
    }
}
