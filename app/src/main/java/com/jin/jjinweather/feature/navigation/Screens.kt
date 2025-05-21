package com.jin.jjinweather.feature.navigation

sealed class Screens(val route: String) {
    object Onboarding : Screens("onboardingScreen")
    object Temperature : Screens("temperatureScreen")
    object Outfit : Screens("outfitScreen/{temperature}?cityName={cityName}?weatherSummary={weatherSummary}") {
        fun createRoute(temperature: Int, cityName: String, weatherSummary: String): String {
            return "outfitScreen/$temperature?cityName=$cityName?weatherSummary=$weatherSummary"
        }
    }

    companion object {
        const val TEMPERATURE = "temperature"
        const val CITY_NAME = "cityName"
        const val WEATHER_SUMMARY = "weatherSummary"
    }
}
