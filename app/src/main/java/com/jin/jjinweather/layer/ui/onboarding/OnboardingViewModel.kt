package com.jin.jjinweather.layer.ui.onboarding

import androidx.lifecycle.ViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.jin.jjinweather.layer.domain.model.PermissionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OnboardingViewModel : ViewModel() {
    // todo : location permission check
    private val _locationPermissionState = MutableStateFlow(PermissionState.DENIED)
    val locationPermissionState: StateFlow<PermissionState> = _locationPermissionState

    @OptIn(ExperimentalPermissionsApi::class)
    fun updateLocationPermissionStatus(status: PermissionStatus) {
        _locationPermissionState.value = when (status) {
            is PermissionStatus.Granted -> PermissionState.GRANTED
            is PermissionStatus.Denied -> {
                if (status.shouldShowRationale) PermissionState.SHOW_RATIONALE
                else PermissionState.DENIED
            }
        }
    }
}
