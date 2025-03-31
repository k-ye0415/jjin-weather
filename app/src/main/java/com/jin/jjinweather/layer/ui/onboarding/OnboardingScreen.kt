package com.jin.jjinweather.layer.ui.onboarding

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.jin.jjinweather.layer.domain.model.PermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun OnboardingScreen(viewModel: OnboardingViewModel, onNavigateToTemperature: () -> Unit) {
    val locationGrantedStatus by viewModel.locationPermissionState.collectAsState()
    val composePermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val weatherState by viewModel.weatherState.collectAsState()

    // 권한 상태 업데이트
    LaunchedEffect(composePermissionState.status) {
        viewModel.updateLocationPermissionStatus(composePermissionState.status)
    }

    // 위치 권한이 승인되었을 때 날씨 로드
    LaunchedEffect(locationGrantedStatus) {
        if (locationGrantedStatus == PermissionState.GRANTED) {
            viewModel.loadWeather()
        }
    }


    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), contentAlignment = Alignment.Center
        ) {
            // todo : tutorial pager
            Column {
                when (locationGrantedStatus) {
                    PermissionState.GRANTED -> {
                        WeatherContentUI(weatherState, onNavigateToTemperature)
                    }

                    else -> {
                        PermissionRequestUi { composePermissionState.launchPermissionRequest() }
                    }
                }
            }
        }
    }
}

@Composable
fun PermissionRequestUi(onRequestPermission: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("날씨 정보를 위해 위치 권한이 필요해요.")
        Button(onClick = onRequestPermission) {
            Text("권한 요청하기")
        }
    }
}
