package com.jin.jjinweather.layer.ui.loading

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun LoadingScreen(viewModel: LoadingViewModel, onNavigateToOnboarding: () -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), contentAlignment = Alignment.Center
        ) {
            // todo : api, isFirstUser(첫실행 user 는 tutorial(onboarding)로, 아니라면 바로 main)
            Column {
                Text("loading Screen", fontSize = 24.sp)
                Button(onClick = onNavigateToOnboarding) { Text("GO TO TUTORIAL") }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingScreenPreview() {
    LoadingScreen(LoadingViewModel(), {})
}
