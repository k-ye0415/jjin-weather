package com.jin.jjinweather.feature.temperature.ui

import androidx.annotation.StringRes

sealed class FormattedDate {
    data class Today(@StringRes val labelRes: Int) : FormattedDate()
    data class Date(val dayOfWeek: String, val month: Int, val day: Int) : FormattedDate()
}
