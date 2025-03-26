package com.jin.jjinweather.layer.ui.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun OnboardingScreen(viewModel: OnboardingViewModel, onNavigateToTemperature: () -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), contentAlignment = Alignment.Center
        ) {
            Column {
                // todo : tutorial pager
                //        permission
                Text("Tutorial Screen", fontSize = 24.sp)
                Button(onClick = onNavigateToTemperature) { Text("GO TO MAIN") }
            }
        }
    }
}
