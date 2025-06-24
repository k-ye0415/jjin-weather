package com.jin.jjinweather.feature.navigation

sealed class Screens(val route: String) {
    object Onboarding : Screens("onboardingScreen")
    object Temperature : Screens("temperatureScreen")
    object Outfit : Screens(
        "outfitScreen/{$PAGE_NUMBER}"
    ) {
        fun createRoute(pageNumber: Int): String = "outfitScreen/$pageNumber"
    }

    object DistrictSearch : Screens("districtSearch")

    companion object {
        const val PAGE_NUMBER = "pageNumber"
    }
}
