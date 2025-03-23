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
import com.jin.jjinweather.layer.ui.onboarding.OnboardingScreen
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

    NavHost(navController, Screens.LOADING.route) {
        composable(Screens.LOADING.route) { LoadingScreen(navController) }
        composable(Screens.ONBOARDING.route) { OnboardingScreen(navController) }
        composable(Screens.TEMPERATURE.route) { TemperatureScreen() }
    }
}
