package com.jin.jjinweather.feature.weather.ui.state

/**
 * FIXME
 * `Idle` 타입을 [UiState]에 추가하면 모든 곳에 대한 컴파일 에러가 발생.
 * State 를 분리할지, [UiState] 를 리펙토링 할지 고민중....
 */
sealed class DistrictState<out T> {
    object Idle : DistrictState<Nothing>()
    object Loading : DistrictState<Nothing>()
    data class Success<T>(val data: T) : DistrictState<T>()
    data class Error(val message: String) : DistrictState<Nothing>()
}
