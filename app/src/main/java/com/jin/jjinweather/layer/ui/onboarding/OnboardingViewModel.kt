package com.jin.jjinweather.layer.ui.onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OnboardingViewModel : ViewModel() {
    // todo : permission
    private val _permission = MutableStateFlow(false)
    val permission: StateFlow<Boolean> = _permission
}
