package com.example.toucheeseapp.ui.screens.tab_Qna

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.toucheeseapp.ui.components.BottomNavigationBarComponent
import com.example.toucheeseapp.ui.components.listitem.InfoListItemComponent
import com.example.toucheeseapp.ui.components.listitem.InfoListItemComponentNoChip
import com.example.toucheeseapp.ui.components.topbar.TopAppBarComponent
import com.example.toucheeseapp.ui.viewmodel.QnaViewModel

@Composable
fun QnaContentScreen(
    selectedTab: Int,
    viewModel: QnaViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onClickLeadingIcon: () -> Unit,
    onTabSelected: (Int) -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBarComponent(
                title = "문의내역",
                leadingIcon = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                showLeadingIcon = true,
                showTrailingIcon = false,
                onClickLeadingIcon = onClickLeadingIcon,
            )
        },
        bottomBar = {
            BottomNavigationBarComponent(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected,
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val testDataList = listOf(
                "테스트 문의1",
                "",
                "테스트 문의3",
                "테스트 문의4",
                "테스트 문의4",
                "테스트 문의4",
                "테스트 문의4",
                "테스트 문의4",
                "테스트 문의4",
                "테스트 문의4",
                "테스트 문의4",
                "테스트 문의4",
                "테스트 문의4",
                "테스트 문의4"
            )

            // 추후에 item으로 바꾸고 단일 데이터를 넣어준다
            items(testDataList){ item ->
                // 사용자 문의 내용
                InfoListItemComponent(
                    title = item,
                    content = "문의 내용입니다.\n문의 내용입니다.문의 내용입니다.문의 내용입니다.문의 내용입니다.문의 내용입니다.문의 내용입니다. 문의 내용입니다.",
                    createDate = "2024-12-15",
                    userName = "홍길동",
                    replyState = true,
                    isContentShowed = true,
                    modifier = Modifier.padding(
                        start = 12.dp,
                        end = 12.dp,
                        bottom = 12.dp,
                        top = 16.dp
                    ),
                    onItemClicked = { Log.d("QnaContentScreen", "문의 item 클릭 : ${item}") }
                )

                Spacer(modifier= Modifier.height(12.dp))

                // 스튜디오 (미미팀) 답변
                InfoListItemComponentNoChip(
                    title = "터치즈 담당자",
                    content = "안녕하세요 OOO님.\n문의내용 답변이 들어갑니다. 감사합니다.",
                    createDate = "2024-11-04",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                        start = 12.dp,
                        end = 12.dp,
                        bottom = 12.dp,
                        top = 16.dp
                    ),
                )

            }
        }


    }

}