package com.toucheese.app.ui.components

import androidx.compose.foundation.layout.*
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
    modifier: Modifier = Modifier,
    name: String,
    phone: String,
    email: String
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "내정보",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // 정보 항목들
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 성함
            InfoRow(label = "성 함", value = name)

            // 연락처
            InfoRow(label = "연락처", value = phone)

            // 이메일
            InfoRow(label = "이메일", value = email)
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
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

        Spacer(modifier = Modifier.width(16.dp)) // 라벨과 값 간 간격

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
fun OrderPayMyInfoComponentPreview() {
    OrderPayMyInfoComponent(
        name = "강미미",
        phone = "010-9593-3561",
        email = "kan9mimi@gmail.com"
    )
}