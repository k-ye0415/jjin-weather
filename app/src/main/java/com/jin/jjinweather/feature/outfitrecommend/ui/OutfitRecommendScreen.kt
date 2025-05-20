package com.jin.jjinweather.feature.outfitrecommend.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jin.jjinweather.R
import com.jin.jjinweather.feature.weather.ui.state.UiState
import com.jin.jjinweather.ui.theme.ButtonColor

@Composable
fun OutfitRecommendScreen(imageUrlState: UiState<String>) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            when (imageUrlState) {
                is UiState.Success -> OutfitSuccess(imageUrlState.data)
                else -> OutfitError()
            }
        }
    }
}

@Composable
private fun OutfitSuccess(imageUrl: String) {
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 20.dp),
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = stringResource(R.string.outfit_success_img_desc)
        )
        Box(
            modifier = Modifier
                .clickable { }
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
                .size(32.dp)
                .padding(2.dp)
        ) {
            // FIXME : 다른 이미지 전환?
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Outlined.Repeat,
                contentDescription = stringResource(R.string.outfit_success_switch_icon_desc),
                tint = Color.White
            )
        }
    }
}

@Composable
private fun OutfitError() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 20.dp),
            painter = painterResource(R.drawable.img_outfit_error),
            contentDescription = stringResource(R.string.outfit_error_img_desc)
        )
        Text(
            text = stringResource(R.string.outfit_error),
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 20.dp)
        )
        //FIXME : 재요청?
        Box(
            modifier = Modifier
                .clickable { }
                .clip(RoundedCornerShape(8.dp))
                .background(ButtonColor)
                .padding(horizontal = 4.dp),
        ) {
            Text(
                text = stringResource(R.string.outfit_error_button_retry),
                fontSize = 10.sp,
                color = Color.White
            )
        }
    }
}
