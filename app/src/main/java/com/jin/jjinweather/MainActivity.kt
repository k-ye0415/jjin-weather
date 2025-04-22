package com.jin.jjinweather

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jin.jjinweather.feature.location.LocationRepository
import com.jin.jjinweather.feature.location.data.LocationRepositoryImpl
import com.jin.jjinweather.feature.locationimpl.data.GeoCodeDataSourceImpl
import com.jin.jjinweather.feature.locationimpl.data.GeoPointDataSourceImpl
import com.jin.jjinweather.feature.weather.WeatherRepository
import com.jin.jjinweather.feature.weather.data.OpenWeatherDataSource
import com.jin.jjinweather.feature.weather.data.OpenWeatherApi
import com.jin.jjinweather.feature.weather.data.WeatherRepositoryImpl
import com.jin.jjinweather.feature.weatherimpl.data.WeatherDataSourceImpl
import com.jin.jjinweather.layer.data.RetrofitClient
import com.jin.jjinweather.feature.database.data.DatabaseProvider
import com.jin.jjinweather.feature.datastore.data.PreferencesRepositoryImpl
import com.jin.jjinweather.layer.domain.usecase.GetCurrentLocationWeatherUseCase
import com.jin.jjinweather.layer.ui.Screens
import com.jin.jjinweather.layer.ui.onboarding.OnboardingScreen
import com.jin.jjinweather.layer.ui.onboarding.OnboardingViewModel
import com.jin.jjinweather.layer.ui.temperature.TemperatureScreen
import com.jin.jjinweather.layer.ui.temperature.TemperatureViewModel
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

        val openWeatherApi: OpenWeatherApi =
            RetrofitClient.createService("https://api.openweathermap.org/data/3.0/")

        val db = DatabaseProvider.getDatabase(this)

        enableEdgeToEdge()
        setContent {
            JJinWeatherTheme {
                AppNavigator(
                    context = this,
                    locationRepository = LocationRepositoryImpl(
                        db.geoPointTrackingDataSource(),
                        GeoPointDataSourceImpl(this),
                        GeoCodeDataSourceImpl(this),
                    ),
                    weatherRepository = WeatherRepositoryImpl(
                        db.weatherTrackingDataSource(),
                        WeatherDataSourceImpl(OpenWeatherDataSource(openWeatherApi)),
                        GeoCodeDataSourceImpl(this),
                    ),
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
) {
    val navController = rememberNavController()

    val onboardingViewModel = OnboardingViewModel(PreferencesRepositoryImpl(context))
    val temperatureViewModel = TemperatureViewModel(
        GetCurrentLocationWeatherUseCase(locationRepository, weatherRepository)
    )

    NavHost(navController, Screens.ONBOARDING.route) {
        composable(Screens.ONBOARDING.route) {
            OnboardingScreen(
                viewModel = onboardingViewModel,
                onNavigateToTemperature = {
                    navController.navigateClearingBackStack(
                        destination = Screens.TEMPERATURE,
                        clearUpTo = Screens.ONBOARDING,
                        inclusive = true
                    )
                }
            )
        }
        composable(Screens.TEMPERATURE.route) {
            TemperatureScreen(
                viewModel = temperatureViewModel,
                onNavigate = {}
            )
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
