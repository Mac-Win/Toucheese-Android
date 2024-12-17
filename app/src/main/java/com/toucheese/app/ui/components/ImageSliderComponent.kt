package com.toucheese.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.rememberAsyncImagePainter

@Composable
fun ImageSliderComponent(images: List<String>, modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { images.size })

    HorizontalPager(
        state = pagerState,
        modifier = modifier
    ) { page ->
        Image(
            painter = rememberAsyncImagePainter(model = images[page]),
            contentDescription = "스튜디오 이미지",
            contentScale = ContentScale.FillBounds,
            modifier = modifier
        )
    }
}