package com.example.toucheeseapp.ui.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.toucheeseapp.R

@Composable
fun DatePickComponent(
    date: String,
    modifier: Modifier = Modifier,
    onValueChanged: () -> Unit,
) {
    Column(
        modifier = modifier.padding(vertical = 16.dp)
    ) {
        Text(
            text = "촬영날짜",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(bottom = 8.dp),
        )

        // 예약 날짜 선택
        OutlinedTextField(
            value = date,
            maxLines = 1, // 최대 한 줄
            onValueChange = { onValueChanged() },
            placeholder = { Text("예약일자 및 시간 선택") },
            modifier = modifier
                .fillMaxWidth()
                .clickable(
                    enabled = true,
                    role = Role.Button
                ) {
                    Log.d("DatePickComponent", "Date Clicked")

                },

            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.calendar_24px),
                    contentDescription = "Calendar",
                    tint = Color.Gray
                )
            }
        )
    }
}
