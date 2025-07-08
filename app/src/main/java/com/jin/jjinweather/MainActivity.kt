package com.jin.jjinweather

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.jin.jjinweather.feature.datastore.data.PreferencesRepositoryImpl
import com.jin.jjinweather.feature.district.ui.DistrictSearchScreen
import com.jin.jjinweather.feature.district.ui.DistrictSearchViewModel
import com.jin.jjinweather.feature.googleplaces.data.PlacesRepositoryImpl
import com.jin.jjinweather.feature.googleplaces.domain.PlacesRepository
import com.jin.jjinweather.feature.googleplaces.domain.usecase.SearchDistrictUseCase
import com.jin.jjinweather.feature.googleplacesimpl.data.PlacesDataSourceImpl
import com.jin.jjinweather.feature.location.LocationRepository
import com.jin.jjinweather.feature.location.data.LocationRepositoryImpl
import com.jin.jjinweather.feature.location.domain.SaveDistrictAndRequestWeatherUseCase
import com.jin.jjinweather.feature.locationimpl.data.GeoCodeDataSourceImpl
import com.jin.jjinweather.feature.locationimpl.data.GeoPointDataSourceImpl
import com.jin.jjinweather.feature.navigation.Screens
import com.jin.jjinweather.feature.network.GooglePlacesApiClient
import com.jin.jjinweather.feature.network.OpenAiApiClient
import com.jin.jjinweather.feature.network.OpenWeatherApiClient
import com.jin.jjinweather.feature.onboarding.ui.OnboardingScreen
import com.jin.jjinweather.feature.onboarding.ui.OnboardingViewModel
import com.jin.jjinweather.feature.outfit.data.OutfitRepositoryImpl
import com.jin.jjinweather.feature.outfit.domain.OutfitRepository
import com.jin.jjinweather.feature.outfitImpl.OpenAiDataSourceImpl
import com.jin.jjinweather.feature.outfitrecommend.ui.OutfitScreen
import com.jin.jjinweather.feature.outfitrecommend.ui.OutfitViewModel
import com.jin.jjinweather.feature.temperature.ui.TemperatureScreen
import com.jin.jjinweather.feature.temperature.ui.TemperatureViewModel
import com.jin.jjinweather.feature.weather.data.WeatherRepositoryImpl
import com.jin.jjinweather.feature.weather.domain.repository.WeatherRepository
import com.jin.jjinweather.feature.weather.domain.usecase.GetAllLocationAndWeatherUseCase
import com.jin.jjinweather.feature.weather.domain.usecase.GetOutfitRecommendUseCase
import com.jin.jjinweather.feature.weatherimpl.data.WeatherDataSourceImpl
import com.jin.jjinweather.ui.theme.JJinWeatherTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        var keepSplashScreen = true
        splashScreen.setKeepOnScreenCondition { keepSplashScreen }

        lifecycleScope.launch {
            delay(1000L)
            keepSplashScreen = false
        }

        val openWeatherApi = OpenWeatherApiClient.createService()
        val chatGptApi = OpenAiApiClient.createService(BuildConfig.CHAT_GPT_API_KEY)
        val googlePlacesApi = GooglePlacesApiClient.createService()

        val db = Room.databaseBuilder(this, AppDatabase::class.java, "weather_db").build()

        enableEdgeToEdge()
        setContent {
            JJinWeatherTheme {
                AppNavigator(
                    context = this,
                    locationRepository = LocationRepositoryImpl(
                        db.geoPointTrackingDataSource(),
                        db.cityNameTrackingDataSource(),
                        GeoPointDataSourceImpl(this),
                        GeoCodeDataSourceImpl(this),
                    ),
                    weatherRepository = WeatherRepositoryImpl(
                        db.weatherTrackingDataSource(),
                        WeatherDataSourceImpl(openWeatherApi, BuildConfig.OPEN_WEATHER_API_KEY),
                    ),
                    outfitRepository = OutfitRepositoryImpl(
                        openAiDataSource = OpenAiDataSourceImpl(
                            chatGPTApi = chatGptApi
                        )
                    ),
                    placesRepository = PlacesRepositoryImpl(
                        placesDataSource = PlacesDataSourceImpl(googlePlacesApi)
                    )
                )
            }
        }
    }
}

@Composable
fun AppNavigator(
    context: Context,
    locationRepository: LocationRepository,
    weatherRepository: WeatherRepository,
    outfitRepository: OutfitRepository,
    placesRepository: PlacesRepository
) {
    val navController = rememberNavController()

    val onboardingViewModel = OnboardingViewModel(PreferencesRepositoryImpl(context))
    val temperatureViewModel = TemperatureViewModel(
        GetAllLocationAndWeatherUseCase(locationRepository, weatherRepository)
    )
    val districtSearchViewModel = DistrictSearchViewModel(
        SearchDistrictUseCase(placesRepository),
        SaveDistrictAndRequestWeatherUseCase(locationRepository, weatherRepository),
        GetAllLocationAndWeatherUseCase(locationRepository, weatherRepository)
    )

    NavHost(navController, Screens.Onboarding.route) {
        composable(Screens.Onboarding.route) {
            OnboardingScreen(
                viewModel = onboardingViewModel,
                onNavigateToTemperature = {
                    navController.navigateClearingBackStack(
                        destination = Screens.Temperature,
                        clearUpTo = Screens.Onboarding,
                        inclusive = true
                    )
                }
            )
        }
        composable(Screens.Temperature.route) {
            TemperatureScreen(
                viewModel = temperatureViewModel,
                onNavigateToOutfit = { pageNumber ->
                    val route = Screens.Outfit.createRoute(pageNumber)
                    navController.navigate(route)
                },
                onNavigateToDistrict = {
                    navController.navigate(Screens.DistrictSearch.route)
                }
            )
        }
        composable(
            route = Screens.Outfit.route,
            arguments = listOf(navArgument(Screens.PAGE_NUMBER) { type = NavType.IntType })
        ) { backStackEntry ->
            val pageNumber = backStackEntry.arguments?.getInt(Screens.PAGE_NUMBER) ?: 0
            val outfitViewModel = remember {
                OutfitViewModel(
                    GetOutfitRecommendUseCase(locationRepository, weatherRepository, outfitRepository)
                )
            }
            OutfitScreen(
                viewModel = outfitViewModel,
                pageNumber = pageNumber,
                onNavigateToTemperature = { navController.navigate(Screens.Temperature.route) }
            )
        }
        composable(Screens.DistrictSearch.route) {
            DistrictSearchScreen(districtSearchViewModel) {
                navController.navigate(Screens.Temperature.route)
            }
        }
    }
}

fun NavController.navigateClearingBackStack(
    destination: Screens,
    clearUpTo: Screens,
    inclusive: Boolean
) {
    this.navigate(destination.route) {
        popUpTo(clearUpTo.route) {
            this.inclusive = inclusive
        }
    }
}
