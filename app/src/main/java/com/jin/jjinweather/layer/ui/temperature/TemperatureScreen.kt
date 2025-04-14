package com.jin.jjinweather.layer.ui.temperature

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.jin.jjinweather.layer.domain.model.PermissionState
import com.jin.jjinweather.layer.ui.common.OnboardingWeatherViewModel
import com.jin.jjinweather.layer.ui.onboarding.WeatherContentUI

@Composable
fun TemperatureScreen(viewModel: OnboardingWeatherViewModel, onNavigate: () -> Unit) {
    val composePermissionState = viewModel.permissionState.collectAsState()
    val weather by viewModel.weatherState.collectAsState()

    LaunchedEffect(Unit) {
        if (composePermissionState.value == PermissionState.GRANTED) {
            viewModel.onTemperatureScreenEntered()
        }
    }

    WeatherContentUI(weather)
}
