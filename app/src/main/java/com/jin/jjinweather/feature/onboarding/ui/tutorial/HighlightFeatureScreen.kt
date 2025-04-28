package com.jin.jjinweather.feature.onboarding.ui.tutorial

import androidx.compose.animation.core.EaseInOutCubic
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.jjinweather.R
import com.jin.jjinweather.ui.theme.JJinWeatherTheme

@Composable
fun HighlightFeatureScreen() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFffa32c)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(R.drawable.bg_highlight_feature_top),
            contentDescription = "top"
        )

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 140.dp)
        ) {
            Text(
                "어제보다 추운지 더운지",
                color = Color.White,
                fontSize = 24.sp,
            )
            Text(
                "확인할 수 있어요!",
                color = Color.White,
                fontSize = 24.sp,
            )
        }

        // FIXME : App Icon 이 필요함으로 App icon 정의 후 조금 더 다듬어질 예정
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(vertical = 18.dp, horizontal = 20.dp)) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.ic_main_clear_sky_day),
                    contentDescription = "intro icon",
                    modifier = Modifier
                        .padding(end = 10.dp)
                )
                Text("어제보다 2° 높아요")
                Spacer(Modifier.weight(1f))
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_arrow_upward_24),
                    contentDescription = "arrow",
                    tint = Color(0xFFffa32c)
                )
            }
        }
        Spacer(Modifier.weight(1f))
    }

}

@Composable
@Preview
fun HighlightFeatureScreenPreview() {
    JJinWeatherTheme {
        HighlightFeatureScreen()
    }
}
