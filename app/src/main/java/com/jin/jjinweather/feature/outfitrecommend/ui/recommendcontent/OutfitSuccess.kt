package com.jin.jjinweather.feature.outfitrecommend.ui.recommendcontent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jin.jjinweather.R

@Composable
fun OutfitSuccess(imageUrls: List<String>) {
    var imageIndex by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 20.dp),
    ) {
        AsyncImage(
            model = imageUrls[imageIndex],
            contentDescription = stringResource(R.string.outfit_success_img_desc),
            modifier = Modifier.align(Alignment.Center)
        )
        if (imageUrls.size >= 2) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clickable { imageIndex = (imageIndex + 1) % imageUrls.size }
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)
                    .size(32.dp)
                    .padding(2.dp)
            ) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = Icons.Outlined.Repeat,
                    contentDescription = stringResource(R.string.outfit_success_switch_icon_desc),
                    tint = Color.White
                )
            }
        }
    }
}
