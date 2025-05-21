package com.jin.jjinweather.feature.outfitrecommend.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jin.jjinweather.R
import com.jin.jjinweather.ui.theme.JJinWeatherTheme
import com.jin.jjinweather.ui.theme.LoadingBackgroundColor

@Composable
fun OutfitLoadingScreen() {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LoadingBackgroundColor)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LoadingCarousel()
            Text(
                text = stringResource(R.string.outfit_loading_title),
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.padding(top = 20.dp)
            )
        }
    }
}

@Composable
private fun LoadingCarousel() {
    val clothesImageList = remember {
        listOf(
            R.drawable.ic_outfit_loading_coat,
            R.drawable.ic_outfit_loading_jacket,
            R.drawable.ic_outfit_loading_jeans,
            R.drawable.ic_outfit_loading_dress,
            R.drawable.ic_outfit_loading_rain_coats,
            R.drawable.ic_outfit_loading_shirt_blouse,
            R.drawable.ic_outfit_loading_sweatshirt,
            R.drawable.ic_outfit_loading_tshirt
        )
    }
    val transition = rememberInfiniteTransition(label = "carouselTransition")
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = clothesImageList.size.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000 * clothesImageList.size, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "carouselProgress"
    )
    val currentIndex = progress.toInt() % clothesImageList.size

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 중앙을 기준으로 양옆 아이템 구성
        for (i in -2..2) {
            val index = (currentIndex + i + clothesImageList.size) % clothesImageList.size
            val isCenter = (i == 0)
            val boxSize by animateDpAsState(
                targetValue = if (isCenter) 100.dp else 60.dp,
                label = "boxSize"
            )
            val boxAlpha by animateFloatAsState(
                targetValue = if (isCenter) 1f else 0.5f,
                label = "boxAlpha"
            )

            Box(
                modifier = Modifier
                    .size(boxSize)
                    .graphicsLayer { this.alpha = boxAlpha }
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Crossfade(
                    targetState = clothesImageList[index],
                    label = "imageCrossfade"
                ) { imageRes ->
                    Image(
                        painter = painterResource(imageRes),
                        contentDescription = stringResource(R.string.outfit_loading_img_desc),
                        modifier = Modifier.fillMaxSize(0.9f)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun OutfitLoadingScreenPreview() {
    JJinWeatherTheme {
        OutfitLoadingScreen()
    }
}
