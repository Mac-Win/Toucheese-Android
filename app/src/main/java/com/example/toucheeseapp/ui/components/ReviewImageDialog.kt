package com.example.toucheeseapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp


@Composable
fun ReviewImageDialog(
    photoUrls: List<String>,
    selectedIndex: Int,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.9f))
        ) {
            // 이미지 슬라이더 컴포넌트 호출
            ImageSliderComponent(
                images = photoUrls,
                modifier = Modifier.fillMaxSize()
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
        }
    }
}