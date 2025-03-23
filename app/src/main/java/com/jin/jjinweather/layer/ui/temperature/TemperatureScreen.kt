package com.jin.jjinweather.layer.ui.temperature

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun TemperatureScreen() {

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding), contentAlignment = Alignment.Center) {
            Column {
                Text("Temperature Screen", fontSize = 24.sp)
            }
        }
    }
}
