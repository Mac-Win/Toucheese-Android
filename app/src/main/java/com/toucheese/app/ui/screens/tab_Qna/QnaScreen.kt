package com.toucheese.app.ui.screens.tab_Qna

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.toucheese.app.ui.components.BottomNavigationBarComponent
import com.toucheese.app.ui.components.listitem.InfoListItemComponent
import com.toucheese.app.ui.components.topbar.TopAppBarComponent
import com.toucheese.app.ui.viewmodel.QnaViewModel

@Composable
fun QnaScreen(
    selectedTab: Int,
    viewModel: QnaViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onTabSelected: (Int) -> Unit,
    onItemClicked: () -> Unit,
    onButtonClicked: () -> Unit,
) {
    // LazyColumn의 스크롤 상태 추적
    val listState = rememberLazyListState()

    // FAB의 가시성 상태
    val isFabVisible by remember (listState) {
        derivedStateOf {
            val visibleItemInfo = listState.layoutInfo.visibleItemsInfo
            if (visibleItemInfo.isEmpty()) true
            else {
                val lastVisibleItemIndex = visibleItemInfo.lastOrNull()?.index ?: 0
                lastVisibleItemIndex < (listState.layoutInfo.totalItemsCount - 1)
            }
        }
    }

    Scaffold(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
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
        },

        floatingActionButton = {
            if (isFabVisible){
                FloatingActionButton(
                    shape = RoundedCornerShape(8.dp),
                    containerColor = MaterialTheme.colorScheme.primary,
                    elevation = FloatingActionButtonDefaults.elevation(4.dp),
                    onClick = onButtonClicked,
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                    )
                }
            }
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
            state = listState,
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            // 문의 내역 리스트 - 답변 대기
            items(testDataList) { item ->
                // 문의 내역 아이템
                InfoListItemComponent(
                    title = item,
                    content = "",
                    createDate = "2024-12-15",
                    userName = "홍길동",
                    replyState = item.isNotEmpty(),
                    isContentShowed = false,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(
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