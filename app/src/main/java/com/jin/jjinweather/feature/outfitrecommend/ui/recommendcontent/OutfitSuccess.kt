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
    val imageResLit = imageUrls.map { findImageResId(it.trim()) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 20.dp),
    ) {
        AsyncImage(
            model = imageResLit[imageIndex],
            contentDescription = stringResource(R.string.outfit_success_img_desc),
            modifier = Modifier
                .align(Alignment.Center)
                .size(100.dp)
        )
        if (imageResLit.size >= 2) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clickable { imageIndex = (imageIndex + 1) % imageResLit.size }
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

private fun findImageResId(name: String): Int = when (name) {
    "Puffer" -> R.drawable.img_puffer
    "Thermal Clothes" -> R.drawable.img_thermal_clothes
    "Scarf" -> R.drawable.img_scarf
    "Coat" -> R.drawable.img_trench_coat
    "Cardigan" -> R.drawable.img_cardigan
    "Knit" -> R.drawable.img_sweater
    "Hoodie" -> R.drawable.img_hoodie
    "Jacket" -> R.drawable.img_jacket
    "Jeans" -> R.drawable.img_jeans
    "Shirt" -> R.drawable.img_shirt
    "LongSleeve" -> R.drawable.img_long_sleeve
    "CottonPants" -> R.drawable.img_cotton_pants
    "TShirt" -> R.drawable.img_tshirt
    "Slacks" -> R.drawable.img_slacks
    "Sleeveless" -> R.drawable.img_sleeveless
    "Shorts" -> R.drawable.img_shorts
    else -> R.drawable.img_jeans
}
