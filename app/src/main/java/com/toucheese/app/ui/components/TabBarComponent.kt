package com.toucheese.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TabBarComponent(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    tabTitles: List<String>
) {
    TabRow(
        selectedTabIndex = selectedTabIndex,
        containerColor = Color.White, // TabRow 배경을 흰색으로 설정
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                        .padding(horizontal = 8.dp),
                color = MaterialTheme.colorScheme.primary // Indicator 색상을 primary 색상으로 설정
            )
        },
        modifier = Modifier
            .background(Color.White)
            .padding(vertical = 8.dp)
    ) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                modifier = Modifier
                    .background(Color.White)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp),
                    color = if (selectedTabIndex == index) Color.Black else Color.Gray // 선택 여부에 따라 텍스트 색상 변경
                )
            }
        }
    }
}