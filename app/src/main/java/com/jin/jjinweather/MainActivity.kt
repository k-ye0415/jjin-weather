package com.jin.jjinweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jin.jjinweather.layer.ui.Screens
import com.jin.jjinweather.layer.ui.temperature.TemperatureScreen
import com.jin.jjinweather.layer.ui.loading.LoadingScreen
import com.jin.jjinweather.layer.ui.loading.LoadingViewModel
import com.jin.jjinweather.layer.ui.onboarding.OnboardingScreen
import com.jin.jjinweather.layer.ui.onboarding.OnboardingViewModel
import com.jin.jjinweather.layer.ui.temperature.TemperatureViewModel
import com.jin.jjinweather.ui.theme.JJinWeatherTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        var keepSplashScreen = true
        splashScreen.setKeepOnScreenCondition { keepSplashScreen }
        lifecycleScope.launch {
            delay(1000L)
            keepSplashScreen = false
        }
        enableEdgeToEdge()
        setContent {
            JJinWeatherTheme {
                AppNavigator()
            }
        }
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    // todo 일시적 선언
    val loadingViewModel = LoadingViewModel()
    val onboardingViewModel = OnboardingViewModel()
    val temperatureViewModel = TemperatureViewModel()

    NavHost(navController, Screens.LOADING.route) {
        composable(Screens.LOADING.route) { LoadingScreen(navController, loadingViewModel) }
        composable(Screens.ONBOARDING.route) { OnboardingScreen(navController, onboardingViewModel) }
        composable(Screens.TEMPERATURE.route) { TemperatureScreen(navController, temperatureViewModel) }
    }
}
