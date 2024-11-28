package com.example.toucheeseapp.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart

@Composable
fun BottomActionButtons(modifier: Modifier) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // 장바구니 버튼 (작고 서클 배경 포함)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(start = 16.dp)
                .size(52.dp) // 서클 크기
                .background(Color(0xFFFFF2CC), CircleShape)
        ) {
            Button(
                onClick = {
                    Toast.makeText(context, "아직 완성된 기능이 아닙니다", Toast.LENGTH_SHORT).show()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // 투명 버튼
                contentPadding = PaddingValues(0.dp), // 버튼 내부 여백 제거
                modifier = Modifier.size(36.dp) // 버튼 크기
            ) {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart, // Material Icons의 장바구니 아이콘
                    contentDescription = "장바구니",
                    tint = Color.Black
                )
            }
        }

        // 바로 예약 버튼 (크고 배경색 지정)
        Button(
            onClick = {
                Toast.makeText(context, "아직 완성된 기능이 아닙니다", Toast.LENGTH_SHORT).show()
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC000)), // 바로 예약 배경색
            modifier = Modifier
                .weight(0.6f)
                .fillMaxHeight()
                .padding(16.dp)
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