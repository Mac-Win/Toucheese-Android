package com.toucheese.app.ui.screens.tab_myInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toucheese.app.ui.components.BottomNavigationBarComponent
import com.toucheese.app.ui.components.InfoItem
import com.toucheese.app.ui.components.MyInfoProflieComponent
import com.toucheese.app.ui.components.topbar.TopAppBarComponent
import com.toucheese.app.ui.theme.ToucheeseAppTheme

@Composable
fun MyInfoScreen(
    selectedTab: Int,
    modifier: Modifier = Modifier,
    onTabSelected: (Int) -> Unit,
    onLogoutClicked: () -> Unit,
    onBookListClickedList: () -> Unit,
) {
    Scaffold (
        topBar = {
            TopAppBarComponent(
                title = "내 정보",
                showLeadingIcon = false,
                showTrailingIcon = false
            )
        },
        bottomBar = {
            BottomNavigationBarComponent(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected,
            )
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ){
            MyInfoProflieComponent(
                modifier = Modifier.fillMaxWidth()
            )

            HorizontalDivider(
                color = Color(0xFFFAFAFA),
                thickness = 8.dp
            )

            // 아이콘이 있는 항목
            InfoItem(
                text = "예약한 스튜디오 리스트",
                trailingContent = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                },
                onClick = onBookListClickedList
            )

            HorizontalDivider(
                color = Color(0xFFFAFAFA),
                thickness = 2.dp
            )


            InfoItem(
                text = "스크랩한 스튜디오 리스트",
                trailingContent = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                },
                onClick = {}
            )

            HorizontalDivider(
                color = Color(0xFFFAFAFA),
                thickness = 2.dp
            )

            InfoItem(
                text = "리뷰한 스튜디오 리스트",
                trailingContent = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                },
                onClick = {}
            )

            HorizontalDivider(
                color = Color(0xFFFAFAFA),
                thickness = 2.dp
            )

            // 앱 버전 표시
            InfoItem(
                text = "앱 버전",
                trailingContent = {
                    Text(
                        text = "1.1.0",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                },
                onClick = {}
            )

            HorizontalDivider(
                color = Color(0xFFFAFAFA),
                thickness = 2.dp
            )

            // 아이콘이 없는 항목
            InfoItem(
                text = "로그아웃",
                onClick = onLogoutClicked
            )
        }
    }
}