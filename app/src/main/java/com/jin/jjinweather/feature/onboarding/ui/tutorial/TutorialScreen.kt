package com.jin.jjinweather.feature.onboarding.ui.tutorial

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jin.jjinweather.R
import com.jin.jjinweather.ui.theme.JJinWeatherTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TutorialScreen(onRequestPermission: () -> Unit) {
    val tutorialPages = listOf<@Composable () -> Unit>(
        { WelcomeScreen() },
        { FeatureIntroScreen() },
        { HighlightFeatureScreen() }
    )
    val pagerState = rememberPagerState { tutorialPages.size }
    val coroutineScope = rememberCoroutineScope()
    val navigationBarHeightDp = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding().value.toInt().dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = navigationBarHeightDp)
    ) {
        Box(Modifier.weight(1f)) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                tutorialPages[page]()
            }
            NavigationContent(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 18.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth(),
                pagerState = pagerState,
                coroutineScope = coroutineScope
            )
        }
        TutorialFooterScreen(pagerState.currentPage, onRequestPermission)
    }
}

@Composable
private fun NavigationContent(modifier: Modifier, pagerState: PagerState, coroutineScope: CoroutineScope) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // back button
        NavigationIconButton(
            Icons.Outlined.ArrowBackIosNew,
            stringResource(R.string.tutorial_back_arrow_icon_desc),
            pagerState.currentPage != 0
        ) {
            val prevPage = (pagerState.currentPage - 1).coerceAtLeast(0)
            coroutineScope.launch {
                pagerState.animateScrollToPage(prevPage)
            }
        }
        // indicator
        TutorialIndicator(pagerState)
        // nex button
        NavigationIconButton(
            icon = Icons.Outlined.ArrowForwardIos,
            stringResource(R.string.tutorial_next_arrow_icon_desc),
            pagerState.currentPage != pagerState.pageCount - 1
        ) {
            val nextPage = (pagerState.currentPage + 1).coerceAtMost(pagerState.pageCount - 1)
            coroutineScope.launch {
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }
}

@Composable
private fun NavigationIconButton(
    icon: ImageVector,
    contentDescription: String,
    visible: Boolean,
    onClick: () -> Unit
) {
    val targetAlpha = if (visible) 1f else 0f
    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = 300),
        label = "NavigationButtonAlpha"
    )
    Box(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            .alpha(alpha)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White.copy(alpha = 0.4f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
private fun TutorialIndicator(
    pagerState: PagerState
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { index ->
            val color = if (pagerState.currentPage == index) Color.White else Color.White.copy(alpha = 0.3f)
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}


@Composable
@Preview
fun TutorialScreenPreview() {
    JJinWeatherTheme {
        TutorialScreen({})
    }
}
