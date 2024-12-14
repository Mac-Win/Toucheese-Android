package com.example.toucheeseapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.toucheeseapp.R


@Composable
fun BottomNavigationBarComponent(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    NavigationBar(
        containerColor = Color.White // 바텀 내비의 기본 배경색 설정
    ) {
        val items = listOf(
            BottomNavItem("홈", R.drawable.home_36px),
            BottomNavItem("예약일정", R.drawable.calendar_36px),
            BottomNavItem("문의하기", R.drawable.qna_36px),
            BottomNavItem("내정보", R.drawable.myinfo_36px)
        )

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon), // painterResource를 사용하여 아이콘 렌더링
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFFFC000), // 선택된 아이콘 색상
                    selectedTextColor = Color.Black, // 선택된 텍스트 색상
                    indicatorColor = Color.White, // 선택된 탭 배경색 설정
                    unselectedIconColor = Color(0xFFD9D9D9), // 선택되지 않은 아이콘 색상
                    unselectedTextColor = Color(0xFFD9D9D9)  // 선택되지 않은 텍스트 색상
                )
            )
        }
    }
}

data class BottomNavItem(val title: String, val icon: Int) // icon 타입을 Int로 유지

@Preview
@Composable
fun BottomNavBarPreview() {
    Surface {
        val (selectedTab, setSelectedTab) = remember { mutableStateOf(0) }
        BottomNavigationBarComponent(
            selectedTab = selectedTab,
            onTabSelected = setSelectedTab
        )
    }

}