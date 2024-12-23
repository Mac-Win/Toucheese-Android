package com.toucheese.app.ui.screens.tab_Qna

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.toucheese.app.data.model.qna.load_qnadetail.QnaDetailResponse
import com.toucheese.app.data.token_manager.TokenManager
import com.toucheese.app.ui.components.BottomNavigationBarComponent
import com.toucheese.app.ui.components.listitem.InfoListItemComponent
import com.toucheese.app.ui.components.listitem.InfoListItemComponentNoChip
import com.toucheese.app.ui.components.topbar.TopAppBarComponent
import com.toucheese.app.ui.viewmodel.QnaViewModel

@Composable
fun QnaContentScreen(
    questionId: Int,
    selectedTab: Int,
    tokenManager: TokenManager,
    viewModel: QnaViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onClickLeadingIcon: () -> Unit,
    onTabSelected: (Int) -> Unit,
) {
    // token
    val token = tokenManager.getAccessToken()
    // 특정 문의글
    var qnaDetail by remember { mutableStateOf<QnaDetailResponse?>(null) }
    LaunchedEffect(questionId) {
        qnaDetail = viewModel.loadQnaDetail(token, questionId)
    }

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
            item {
                if (qnaDetail != null){
                    val replyState = qnaDetail!!.answerStatus != "답변대기"
                    // 사용자 문의 내용
                    InfoListItemComponent(
                        title = qnaDetail!!.title,
                        content = qnaDetail!!.content,
                        createDate = qnaDetail!!.createDate,
                        userName = "작성자",
                        replyState = replyState,
                        isContentShowed = qnaDetail!!.content.isNotBlank(),
                        modifier = Modifier.padding(
                            start = 12.dp,
                            end = 12.dp,
                            bottom = 12.dp,
                            top = 16.dp
                        ),
                        onItemClicked = { Log.d("QnaContentScreen", "문의 item 클릭 : ${qnaDetail!!.answerStatus}") }
                    )

                    if (replyState){
                        Spacer(modifier= Modifier.height(12.dp))

                        val answerResponse = qnaDetail!!.answerResponse
                        // 스튜디오 (미미팀) 답변
                        InfoListItemComponentNoChip(
                            title = "터치즈 담당자",
                            content = answerResponse.content,
                            createDate = answerResponse.createDate,
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


    }

}