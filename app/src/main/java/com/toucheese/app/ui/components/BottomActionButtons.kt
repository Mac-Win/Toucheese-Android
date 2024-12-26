package com.toucheese.app.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.toucheese.app.R

@Composable
fun BottomActionButtons(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color.White)
            .padding(8.dp), // 전체 Row에 8.dp 패딩 추가
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // 장바구니 버튼
        Button(
            onClick = {
                Toast.makeText(context, "아직 완성된 기능이 아닙니다", Toast.LENGTH_SHORT).show()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White), // 흰색 배경
            shape = RoundedCornerShape(8.dp), // 라운드드 코너 적용
            contentPadding = PaddingValues(0.dp), // 버튼 내부 여백 제거
            modifier = Modifier
                .size(52.dp)
                .border(1.dp, Color.Black, RoundedCornerShape(8.dp)) // 검정색 테두리 추가
        ) {
            Icon(
                painter = painterResource(id = R.drawable.union),
                contentDescription = "장바구니",
                tint = Color.Black,
                modifier = Modifier.size(24.dp) // 아이콘 크기 조정
            )
        }

        Spacer(modifier = Modifier.width(8.dp)) // 장바구니 버튼과 바로 예약 버튼 사이에 8.dp 간격 추가

        // 바로 예약 버튼 (프라이머리 색상 배경)
        Button(
            onClick = {
                Toast.makeText(context, "아직 완성된 기능이 아닙니다", Toast.LENGTH_SHORT).show()
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary), // 프라이머리 색상
            shape = RoundedCornerShape(8.dp), // 라운드드 코너 적용
            modifier = Modifier
                .weight(1f) // 남은 공간을 모두 차지하도록 설정
                .fillMaxHeight()
        ) {
            Text(
                text = "바로 예약",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}