package com.example.toucheeseapp.ui.screens.tab_Qna

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.toucheeseapp.ui.components.BottomNavigationBarComponent
import com.example.toucheeseapp.ui.components.listitem.InfoListItemComponent
import com.example.toucheeseapp.ui.components.textbutton.IconButtonComponent
import com.example.toucheeseapp.ui.components.topbar.TopAppBarComponent
import com.example.toucheeseapp.ui.viewmodel.QnaViewModel

@Composable
fun QnaScreen(
    selectedTab: Int,
    viewModel: QnaViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onTabSelected: (Int) -> Unit,
    onItemClicked: () -> Unit,
    onButtonClicked: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBarComponent(
                title = "문의하기",
                showLeadingIcon = false,
                showTrailingIcon = false,
            )
        },

        bottomBar = {
            BottomNavigationBarComponent(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected,
            )
        }
    ) { innerPadding ->
        // 임시 데이터 -> 추후 데이터 연결 때 객체 리스트로 전환
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
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // 답변 완료 리스트 - 추후 로직 수정
            val didReplyList = testDataList.filter { item ->
                item.isNotEmpty()
            }

            // 답변 대기 리스트 - 추후 로직 수정
            val didNotReplyList = testDataList.filter { item ->
                item.isEmpty()
            }

            // 문의 내역 리스트 - 답변 대기
            items(didNotReplyList) { item ->
                // 문의 내역 아이템
                InfoListItemComponent(
                    title = "Q.${item}",
                    content = "",
                    createDate = "2024-12-15",
                    userName = "홍길동",
                    replyState = item.isNotEmpty(),
                    isContentShowed = false,
                    modifier = Modifier.padding(
                        start = 12.dp,
                        end = 12.dp,
                        bottom = 12.dp,
                        top = 16.dp
                    ),
                    onItemClicked = onItemClicked,
                )
            }

            // 문의 작성하기 버튼
            item {
                Row(
                    modifier = Modifier.padding(vertical = 24.dp),
                ) {
                    IconButtonComponent(
                        icon = Icons.Default.Add,
                        label = "문의 작성하기",
                        enabled = true,
                        modifier = Modifier.fillMaxWidth(),
                        onButtonClicked = onButtonClicked,
                    )
                }
            }
            item {
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
            }

            items(didReplyList) { item ->
                // 문의 내역 아이템
                InfoListItemComponent(
                    title = "Q.${item}",
                    content = "",
                    createDate = "2024-12-15",
                    userName = "홍길동",
                    replyState = item.isNotEmpty(),
                    isContentShowed = false,
                    modifier = Modifier.padding(
                        start = 12.dp,
                        end = 12.dp,
                        bottom = 12.dp,
                        top = 16.dp
                    ),
                    onItemClicked = onItemClicked,
                )
            }

        }

    }
}