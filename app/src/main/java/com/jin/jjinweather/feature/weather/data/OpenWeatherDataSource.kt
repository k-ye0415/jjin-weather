package com.jin.jjinweather.feature.weather.data

/**
 * OpenWeatherMap API를 통한 원격 날씨 정보를 요청하는 데이터 소스.
 *
 * 이 클래스는 [OpenWeatherApi]를 통해 OpenWeather의 REST API에 접근하며,
 * 현재 날씨 정보와 어제 날씨를 가져온다.
 *
 * 이 클래스는 외부 API 호출에 대한 책임만 가지며,
 * 비즈니스 로직이나 Domain 모델 변환은 담당하지 않는다.
 *
 * @param openWeatherApi Retrofit 기반의 OpenWeather API 정의 인터페이스
 *
 * @see OpenWeatherApi
 */
class OpenWeatherDataSource(private val openWeatherApi: OpenWeatherApi) {
    suspend fun fetchWeather(
        latitude: Double,
        longitude: Double,
        exclude: String,
        units: String,
        lang: String,
        apiKey: String
    ) = openWeatherApi.queryWeather(
        latitude = latitude,
        longitude = longitude,
        exclude = exclude,
        units = units,
        lang = lang,
        apiKey = apiKey
    )

    suspend fun fetchYesterdayWeather(
        latitude: Double,
        longitude: Double,
        dateTime: Long,
        units: String,
        lang: String,
        apiKey: String
    ) = openWeatherApi.queryYesterdayWeather(
        latitude = latitude,
        longitude = longitude,
        dateTime = dateTime,
        units = units,
        lang = lang,
        apiKey = apiKey
    )
}
