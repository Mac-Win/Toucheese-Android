package com.toucheese.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight

@Composable
fun ReviewPhotoComponent(
    modifier: Modifier = Modifier,
    photoUrls: List<String>,
    onPhotoClick: (Int) -> Unit
) {
    // 현재 표시 중인 이미지의 인덱스를 상태로 관리
    var currentIndex by remember { mutableStateOf(0) }

    if (photoUrls.isNotEmpty()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(1f) // 원하는 비율로 조정
        ) {
            // 현재 인덱스에 해당하는 이미지 표시
            Image(
                painter = rememberAsyncImagePainter(model = photoUrls[currentIndex]),
                contentDescription = "리뷰 이미지 ${currentIndex + 1}",
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onPhotoClick(currentIndex) },
                contentScale = ContentScale.Crop
            )

            // 왼쪽(<) 아이콘 버튼
            if (currentIndex > 0) {
                IconButton(
                    onClick = { currentIndex = (currentIndex - 1).coerceAtLeast(0) },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 8.dp)
                        .background(Color.Black.copy(alpha = 0.5f), shape = RoundedCornerShape(4.dp))
                        .size(32.dp) // 정사각형 크기 설정
                ) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = "이전",
                        tint = Color.White
                    )
                }
            }

            // 오른쪽(>) 아이콘 버튼
            if (currentIndex < photoUrls.size - 1) {
                IconButton(
                    onClick = { currentIndex = (currentIndex + 1).coerceAtMost(photoUrls.size - 1) },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)
                        .background(Color.Black.copy(alpha = 0.5f), shape = RoundedCornerShape(4.dp))
                        .size(32.dp) // 정사각형 크기 설정
                ) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowRight,
                        contentDescription = "다음",
                        tint = Color.White
                    )
                }
            }

            // 페이지 인디케이터
            Text(
                text = "${currentIndex + 1} / ${photoUrls.size}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .background(Color.Black.copy(alpha = 0.6f), shape = RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}