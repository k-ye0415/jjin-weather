package com.jin.jjinweather.feature.temperature.ui

import androidx.annotation.StringRes

sealed class ForecastTime {
    data class RelativeDay(@StringRes val labelRes: Int) : ForecastTime()
    data class Hour(val hour: Int) : ForecastTime()
}
