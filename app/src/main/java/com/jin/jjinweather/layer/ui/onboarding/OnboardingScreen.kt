package com.jin.jjinweather.layer.ui.onboarding

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun OnboardingScreen(viewModel: OnboardingViewModel, onNavigateToTemperature: () -> Unit) {
    val composePermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_COARSE_LOCATION
    )

    LaunchedEffect(composePermissionState.status) {
        if (composePermissionState.status is PermissionStatus.Granted) {
            viewModel.onLocationPermissionGranted()
            onNavigateToTemperature()
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), contentAlignment = Alignment.Center
        ) {
            TutorialScreen { composePermissionState.launchPermissionRequest() }
        }
    }
}

@Composable
fun TutorialScreen(onRequestPermission: () -> Unit) {
    val pages = listOf("Hello", "World", "Started")
    val pagerState = rememberPagerState { 3 }

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = pages[page],
                    fontSize = 24.sp
                )
            }
        }
        if (pagerState.currentPage == pages.lastIndex) {
            Button(
                onClick = onRequestPermission,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            ) {
                Text(
                    text = "권한 요청하기"
                )
            }
        }
    }
}
