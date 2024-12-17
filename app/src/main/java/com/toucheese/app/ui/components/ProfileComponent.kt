package com.toucheese.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ProfileComponent(
    modifier: Modifier = Modifier,
    profileDrawableRes: Int, // 드로어블 리소스를 ID로 받음
    profileNickname: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFFFF2CC)) // 프로필 섹션 배경색
            .padding(16.dp), // 내부 패딩
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 프로필 이미지
        Image(
            painter = painterResource(id = profileDrawableRes), // 드로어블 리소스 사용
            contentDescription = "프로필 이미지",
            modifier = Modifier
                .size(60.dp) // 서클 크기
                .background(Color.Gray, CircleShape), // 백그라운드 및 서클
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp)) // 이미지와 닉네임 간 간격

        // 프로필 닉네임
        Text(
            text = profileNickname,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black // 텍스트 색상
        )
    }
}