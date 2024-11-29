package com.example.toucheeseapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.toucheeseapp.R

@Composable
fun ShareBottomSheetComponent(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 제목
        Text(
            text = "공유하기",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 공유 버튼 행
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ShareButton(
                iconRes = R.drawable.insta,
                description = "Instagram",
                label = "인스타그램",
                onClick = { onDismiss() }
            )
            ShareButton(
                iconRes = R.drawable.kakaotalk,
                description = "KakaoTalk",
                label = "카카오톡",
                onClick = { onDismiss() }
            )
            ShareButton(
                iconRes = R.drawable.facebook,
                description = "Facebook",
                label = "페이스북",
                onClick = { onDismiss() }
            )
            ShareButton(
                iconRes = R.drawable.link,
                description = "Copy Link",
                label = "링크 복사",
                onClick = { onDismiss() }
            )
        }
    }
}