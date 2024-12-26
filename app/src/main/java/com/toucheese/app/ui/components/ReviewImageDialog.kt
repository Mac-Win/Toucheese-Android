package com.toucheese.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.compose.material3.AlertDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import coil3.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewImageDialog(
    photoUrls: List<String>,
    selectedIndex: Int,
    onDismiss: () -> Unit
) {
    var currentIndex by remember { mutableStateOf(selectedIndex) }
    var accumulatedDrag by remember { mutableStateOf(0f) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onHorizontalDrag = { change, dragAmount ->
                                change.consume()
                                accumulatedDrag += dragAmount
                            },
                            onDragEnd = {
                                if (accumulatedDrag > 100) {
                                    currentIndex = (currentIndex - 1 + photoUrls.size) % photoUrls.size
                                } else if (accumulatedDrag < -100) {
                                    currentIndex = (currentIndex + 1) % photoUrls.size
                                }
                                accumulatedDrag = 0f
                            }
                        )
                    }
            ) {
                // 닫기 버튼을 화면의 오른쪽 상단에 배치
                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "닫기",
                        tint = Color.White
                    )
                }

                // 이미지와 페이지 인디케이터를 중앙에 배치
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    // 현재 선택된 이미지 표시
                    AsyncImage(
                        model = photoUrls.getOrNull(currentIndex),
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .fillMaxWidth(1f)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 페이지 인디케이터
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(photoUrls.size) { index ->
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .size(if (index == currentIndex) 12.dp else 8.dp)
                                    .background(
                                        color = if (index == currentIndex) Color.Yellow else Color.White,
                                        shape = CircleShape
                                    )
                            )
                        }
                    }
                }
            }
        }
    )
}
