package com.toucheese.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter

@Composable
fun ReviewPhotoComponent(
    modifier: Modifier = Modifier,
    photoUrls: List<String>,
    onPhotoClick: (Int) -> Unit
) {
    Row(modifier = modifier.fillMaxWidth()) {
        // 첫 번째 사진 (세로로 길게)
        if (photoUrls.isNotEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(model = photoUrls[0]),
                contentDescription = "리뷰 이미지 1",
                modifier = Modifier
                    .weight(2f) // 세로로 큰 비율
                    .aspectRatio(1f / 2f) // 세로로 길게
                    .padding(end = 4.dp) // 오른쪽 간격
                    .clickable { onPhotoClick(0) }
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // 두 번째와 세 번째 사진 (세로로 쌓아서 배치)
        Column(modifier = Modifier.weight(2f)) {
            if (photoUrls.size > 1) {
                Image(
                    painter = rememberAsyncImagePainter(model = photoUrls[1]),
                    contentDescription = "리뷰 이미지 2",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f) // 정사각형 비율
                        .padding(bottom = 4.dp) // 아래 간격
                        .fillMaxSize()
                        .clickable { onPhotoClick(1) },
                    contentScale = ContentScale.Crop
                )
            }

            if (photoUrls.size > 2) {
                Image(
                    painter = rememberAsyncImagePainter(model = photoUrls[2]),
                    contentDescription = "리뷰 이미지 3",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f) // 정사각형 비율
                        .fillMaxSize()
                        .clickable { onPhotoClick(2) },
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}
