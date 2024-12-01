package com.example.toucheeseapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import coil3.compose.AsyncImage

@Composable
fun ReviewImageDialog(
    photoUrls: List<String>,
    selectedIndex: Int,
    onDismiss: () -> Unit
) {
    var currentIndex by remember { mutableStateOf(selectedIndex) }
    var accumulatedDrag by remember { mutableStateOf(0f) }

    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .size(600.dp, 600.dp)
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume() // 제스처 이벤트 소비
                            accumulatedDrag += dragAmount // 드래그 누적값 추가
                        },
                        onDragEnd = {
                            // 드래그 완료 시 임계값 확인
                            if (accumulatedDrag > 100) { // 오른쪽으로 스와이프 (임계값 100)
                                currentIndex = (currentIndex - 1 + photoUrls.size) % photoUrls.size
                            } else if (accumulatedDrag < -100) { // 왼쪽으로 스와이프
                                currentIndex = (currentIndex + 1) % photoUrls.size
                            }
                            accumulatedDrag = 0f // 누적 드래그 초기화
                        }
                    )
                }
        ) {
            // 현재 선택된 이미지 표시
            AsyncImage(
                model = photoUrls.getOrNull(currentIndex),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .fillMaxWidth() // 가로 전체 채우기
                    .align(Alignment.Center), // 중앙 정렬
                contentScale = ContentScale.FillBounds // 화면에 맞추기
            )

            // 닫기 버튼
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "닫기 버튼",
                tint = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .clickable { onDismiss() }
            )
            // 인디케이터 (하단)
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            ) {
                Indicators(
                    currentIndex = currentIndex,
                    total = photoUrls.size
                )
            }
        }
    }
}

@Composable
fun Indicators(currentIndex: Int, total: Int) {
    Row(
        modifier = Modifier.padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(total) { index ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(if (index == currentIndex) 12.dp else 8.dp)
                    .background(
                        color = if (index == currentIndex) Color.White else Color.Gray,
                        shape = androidx.compose.foundation.shape.CircleShape
                    )
            )
        }
    }
}