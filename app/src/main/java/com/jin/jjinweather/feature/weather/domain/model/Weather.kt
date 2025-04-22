package com.jin.jjinweather.feature.weather.domain.model

/**
 * @property yesterdayTemperature API 경로 분리
 * @property minTemperature daily 에서 첫번째 인덱스 값으로 적용
 * @property maxTemperature daily 에서 첫번째 인덱스 값으로 적용
 * @property iconCode d 와 n는 day 와 night.
 * - 01d or 01n -> clear sky
 * - 02d or 02n -> few clouds
 * - 03d or 03n -> scattered clouds
 * - 04d or 04n -> broken clouds
 * - 09d or 09n -> shower rain
 * - 10d or 10n -> rain
 * - 11d or 11n -> thunderstorm
 * - 13d or 13n -> snow
 * - 50d or 50n -> mist
 * @property moonPhase 값의 범위는 0.0부터 1.0 까지 이며, 주요 값은 다음과 같다.
 * - daily 에서 첫번째 인덱스 값으로 적용
 * - 0.0 또는 1.0 -> New Moon(삭, 달이 태양과 일직선, 보이지 않음.)
 * - 0.25 -> First Quarter (상현달, 오늘쪽 반달)
 * - 0.5 -> Full Moon (보름달, 완전한 둥근달)
 * - 0.75 -> Last Quarter (하현달, 왼쪽 반달)
 */
data class Weather(
    val cityName: String,
    val iconCode: String,
    val currentTemperature: Number,
    val yesterdayTemperature: Number,
    val minTemperature: Number,
    val maxTemperature: Number,
    val hourlyWeatherList: List<HourlyWeather>,
    val dailyWeatherList: List<DailyWeather>,
    val sunrise: Long,
    val sunset: Long,
    val moonPhase: Double
)
