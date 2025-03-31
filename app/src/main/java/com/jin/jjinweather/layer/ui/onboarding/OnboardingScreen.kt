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
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.jin.jjinweather.layer.domain.model.PermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun OnboardingScreen(viewModel: OnboardingViewModel, onNavigateToTemperature: () -> Unit) {
    val permissionState by viewModel.locationPermissionState.collectAsState()
    val locationPermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_COARSE_LOCATION
    )

    LaunchedEffect(locationPermissionState.status) {
        viewModel.updateLocationPermissionStatus(locationPermissionState.status)
    }


    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), contentAlignment = Alignment.Center
        ) {
            Column {
                // todo : tutorial pager
                //        permission
                Text("Tutorial Screen", fontSize = 24.sp)
                when (permissionState) {
                    PermissionState.GRANTED -> {
                        Button(onClick = onNavigateToTemperature) {
                            Text("GO TO MAIN")
                        }
                    }

                    else -> {
                        Text("날씨 정보를 위해 위치 권한이 필요해요.")
                        Button(onClick = {
                            locationPermissionState.launchPermissionRequest()
                        }) {
                            Text("권한 요청하기")
                        }
                    }
                }
            }
        }
    }
}
