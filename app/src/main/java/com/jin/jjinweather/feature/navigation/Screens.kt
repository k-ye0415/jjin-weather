package com.jin.jjinweather.feature.navigation

import android.net.Uri

sealed class Screens(val route: String) {
    object Onboarding : Screens("onboardingScreen")
    object Temperature : Screens("temperatureScreen")
    object Outfit : Screens("outfitScreen/{$TEMPERATURE}/{$CITY_NAME}/{$WEATHER_SUMMARY}/{$HOURLY_FORECAST}") {
        fun createRoute(temperature: Int, cityName: String, weatherSummary: String, forecastJson: String): String {
            val forecastEncoded = Uri.encode(forecastJson)
            return "outfitScreen/$temperature/$cityName/$weatherSummary/$forecastEncoded"
        }
    }

    companion object {
        const val TEMPERATURE = "temperature"
        const val CITY_NAME = "cityName"
        const val WEATHER_SUMMARY = "weatherSummary"
        const val HOURLY_FORECAST = "hourlyForecast"
    }
}
