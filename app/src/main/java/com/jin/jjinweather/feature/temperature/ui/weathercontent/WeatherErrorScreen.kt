package com.jin.jjinweather.feature.temperature.ui.weathercontent

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.jjinweather.R

@Composable
fun WeatherErrorScreen(message: String) {
    val offsetX = remember { Animatable(-60f) }

    LaunchedEffect(Unit) {
        while (true) {
            offsetX.animateTo(
                targetValue = 60f,
                animationSpec = tween(durationMillis = 1500, easing = LinearEasing)
            )
            offsetX.animateTo(
                targetValue = -60f,
                animationSpec = tween(durationMillis = 1500, easing = LinearEasing)
            )
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .padding(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier.size(92.dp),
                        painter = painterResource(R.drawable.ic_main_few_clouds_day),
                        contentDescription = ""
                    )

                    Text(
                        text = "🔍",
                        fontSize = 40.sp,
                        modifier = Modifier
                            .padding(top = 100.dp)
                            .offset(x = offsetX.value.dp, y = (-30).dp)
                            .graphicsLayer {
                                rotationZ = offsetX.value / 6
                            }
                    )
                }
                Text(message, color = Color.Red)
            }
        }
    }
}
