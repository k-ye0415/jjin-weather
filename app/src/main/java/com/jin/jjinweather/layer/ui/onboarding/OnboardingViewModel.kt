package com.jin.jjinweather.layer.ui.onboarding

import androidx.lifecycle.ViewModel
import com.jin.jjinweather.layer.domain.model.PermissionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OnboardingViewModel : ViewModel() {
    // todo : location permission check
    private val _locationPermissionState = MutableStateFlow(PermissionState.DENIED)
    val locationPermissionState: StateFlow<PermissionState> = _locationPermissionState
}
