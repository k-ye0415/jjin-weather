package com.jin.jjinweather.feature.weatherimpl.data

import com.jin.jjinweather.BuildConfig
import com.jin.jjinweather.feature.weather.data.OpenWeatherApi
import com.jin.jjinweather.feature.weather.data.OpenWeatherDataSource
import com.jin.jjinweather.feature.weather.data.model.dto.HistoricalWeatherDTO
import java.time.Instant

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
class OpenWeatherDataSourceImpl(private val openWeatherApi: OpenWeatherApi) : OpenWeatherDataSource {
    private val exclude = "minutely"
    private val units = "metric"
    private val lang = "kr"
    private val apiKey = BuildConfig.OPEN_WEATHER_API_KEY

    override suspend fun fetchWeather(
        latitude: Double,
        longitude: Double
    ) = openWeatherApi.queryWeather(
        latitude = latitude,
        longitude = longitude,
        exclude = exclude,
        units = units,
        lang = lang,
        apiKey = apiKey
    )

    override suspend fun fetchYesterdayWeather(
        latitude: Double,
        longitude: Double
    ): HistoricalWeatherDTO {
        val timestamp24hAgo = Instant.now()
            .minusSeconds(60 * 60 * 24)
            .epochSecond
        return openWeatherApi.queryHistoricalWeather(
            latitude = latitude,
            longitude = longitude,
            dateTime = timestamp24hAgo,
            units = units,
            lang = lang,
            apiKey = apiKey
        )
    }
}
