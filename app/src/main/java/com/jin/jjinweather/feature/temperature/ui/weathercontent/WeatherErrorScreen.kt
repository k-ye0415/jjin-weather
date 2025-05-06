package com.jin.jjinweather.feature.temperature.ui.weathercontent

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.jjinweather.R
import com.jin.jjinweather.ui.theme.ButtonColor
import com.jin.jjinweather.ui.theme.ErrorBackgroundColor
import com.jin.jjinweather.ui.theme.JJinWeatherTheme
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun WeatherErrorScreen(message: String) {
    val angle = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            angle.animateTo(
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 4000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
            angle.snapTo(0f)
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ErrorBackgroundColor)
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(160.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(100.dp),
                    painter = painterResource(R.drawable.ic_main_thunderstorm),
                    contentDescription = stringResource(R.string.error_background_img_desc)
                )
                Image(
                    painter = painterResource(R.drawable.ic_reading_glasses),
                    contentDescription = stringResource(R.string.error_glasses_img_desc),
                    modifier = Modifier
                        .size(64.dp)
                        .graphicsLayer {
                            val radius = 48.dp
                            val angleRad = Math.toRadians(angle.value.toDouble())
                            val offsetX = cos(angleRad) * radius.toPx()
                            val offsetY = sin(angleRad) * radius.toPx()
                            translationX = offsetX.toFloat()
                            translationY = offsetY.toFloat()
                        }
                )
            }

            Spacer(Modifier.height(16.dp))
            Text(stringResource(R.string.error_title), color = Color.White)
            // FIXME : error 보일지 말지 고민중
            Text(message, color = Color.Gray, fontSize = 8.sp)
            // FIXME : 다시 시도?
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = ButtonColor,
                    containerColor = Color.White,
                ),
            ) {
                Text(
                    stringResource(R.string.error_button_retry),
                    modifier = Modifier.padding(vertical = 4.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
@Preview
fun WeatherErrorScreenPreview() {
    JJinWeatherTheme {
        WeatherErrorScreen("Error")
    }
}
