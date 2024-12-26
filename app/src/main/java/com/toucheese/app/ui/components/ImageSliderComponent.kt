package com.toucheese.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter

@Composable
fun ImageSliderComponent(images: List<String>, modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { images.size })

    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Image(
                painter = rememberAsyncImagePainter(model = images[page]),
                contentDescription = "스튜디오 이미지",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = "${pagerState.currentPage + 1} / ${images.size}",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .background(
                    color = Color.Black.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}