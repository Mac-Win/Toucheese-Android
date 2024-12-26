package com.toucheese.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OrderPayMyInfoComponent(
    name: String,
    phone: String,
    email: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        // 성함
        InfoRow(label = "성 함", value = name)

        // 연락처
        InfoRow(label = "연락처", value = phone)

        // 이메일
        InfoRow(label = "이메일", value = email)
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically // 수직 정렬
    ) {
        // 라벨
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .width(80.dp) // 라벨의 고정 너비 설정
        )

        Spacer(modifier = Modifier.width(12.dp)) // 라벨과 값 간 간격

        // 값
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.weight(1f) // 값은 남은 공간을 차지
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderPayMyInfoComponentPreview() {
    OrderPayMyInfoComponent(
        name = "강미미",
        phone = "010-9593-3561",
        email = "kan9mimi@gmail.com"
    )
}