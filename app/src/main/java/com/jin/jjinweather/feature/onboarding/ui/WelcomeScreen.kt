package com.jin.jjinweather.feature.onboarding.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.jjinweather.R
import com.jin.jjinweather.ui.theme.JJinWeatherTheme

@Composable
fun WelcomeScreen() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF458CFE)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(R.drawable.bg_welcome_top),
            contentDescription = "top"
        )

        // Text
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                "안녕하세요,",
                color = Color.White,
                fontSize = 24.sp
            )
            Text(
                "저는 당신의 날씨 비서에요!",
                color = Color.White,
                fontSize = 24.sp
            )
        }

        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(R.drawable.bg_welcome_decoration),
                contentDescription = "Background Decoration",
            )
            Image(
                painter = painterResource(id = R.drawable.ic_main_clear_sky_day),
                contentDescription = "Rotating Icon",
                modifier = Modifier
                    .size(120.dp)
                    .rotate(rotation),
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }

}

@Composable
@Preview(showBackground = true)
fun IntroScreenPreview() {
    JJinWeatherTheme {
        WelcomeScreen()
    }
}
