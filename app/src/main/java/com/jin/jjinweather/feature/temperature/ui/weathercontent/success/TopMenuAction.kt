package com.jin.jjinweather.feature.temperature.ui.weathercontent.success

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Dehaze
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun TopMenuAction() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton({}) {
            Icon(
                imageVector = Icons.Outlined.Dehaze,
                contentDescription = "더보기",
                tint = Color.White
            )
        }
        IconButton({}) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "추가",
                tint = Color.White
            )
        }
    }
}
