package com.jin.jjinweather.feature.district.ui.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.jin.jjinweather.R

@Composable
fun DistrictSearchHeader(onNavigateToTemperature: () -> Unit) {
    // header
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        IconButton(onNavigateToTemperature) {
            Icon(
                imageVector = Icons.Outlined.ArrowBackIosNew,
                contentDescription = stringResource(R.string.tutorial_back_arrow_icon_desc),
                tint = Color.Black
            )
        }
        Text(
            text = stringResource(R.string.district_title),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
    }
}
