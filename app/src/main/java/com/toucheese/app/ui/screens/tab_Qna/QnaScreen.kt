package com.toucheese.app.ui.screens.tab_Qna

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.toucheese.app.data.token_manager.TokenManager
import com.toucheese.app.ui.components.BottomNavigationBarComponent
import com.toucheese.app.ui.components.listitem.InfoListItemComponent
import com.toucheese.app.ui.components.topbar.TopAppBarComponent
import com.toucheese.app.ui.viewmodel.QnaViewModel

@Composable
fun QnaScreen(
    selectedTab: Int,
    tokenManager: TokenManager,
    viewModel: QnaViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onTabSelected: (Int) -> Unit,
    onItemClicked: (Int) -> Unit,
    onButtonClicked: () -> Unit,
) {
    // LazyColumn의 스크롤 상태 추적
    val listState = rememberLazyListState()
    // 토큰
    val token = tokenManager.getAccessToken()
    // 자신의 모든 문의 글 리스트
    val qnaList by viewModel.qnaList.collectAsState()
    LaunchedEffect(Unit) {
        // 자신의 모든 문의 글 리스트 조회
        viewModel.loadQnaList(token)
    }

    // FAB의 가시성 상태
    val isFabVisible by remember (listState) {
        derivedStateOf {
            val visibleItemInfo = listState.layoutInfo.visibleItemsInfo
            val totalItemsCount = listState.layoutInfo.totalItemsCount

            if (visibleItemInfo.isEmpty() || totalItemsCount <= 4) true
            else {
                val lastVisibleItemIndex = visibleItemInfo.lastOrNull()?.index ?: 0
                lastVisibleItemIndex < (totalItemsCount - 1)
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

        LazyColumn(
            state = listState,
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            // 문의 내역 리스트 - 답변 대기
            items(qnaList) { item ->
                Log.d("QnaScreen", "답변 상태 : ${item.answerStatus}")
                // 문의 내역 아이템
                InfoListItemComponent(
                    title = item.title,
                    content = item.content,
                    createDate = item.createDate,
                    userName = "작성자",
                    replyState = item.answerStatus != "답변대기",
                    isContentShowed = false,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(
                        start = 12.dp,
                        end = 12.dp,
                        bottom = 12.dp,
                        top = 16.dp
                    ),
                    onItemClicked ={
                        val questionId = item.id
                        onItemClicked(questionId)
                    },
                )
            }
        }

    }
}