package com.toucheese.app.ui.screens.tab_bookSchedule

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.toucheese.app.data.token_manager.TokenManager
import com.toucheese.app.ui.components.BookingScheduleItemComponent
import com.toucheese.app.ui.components.BottomNavigationBarComponent
import com.toucheese.app.ui.components.topbar.TopAppBarComponent
import com.toucheese.app.ui.viewmodel.BookScheduleViewModel

private const val TAG = "BookScheduleScreen"

@Composable
fun BookScheduleScreen(
    selectedTab: Int,
    tokenManager: TokenManager,
    viewModel: BookScheduleViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onTabSelected: (Int) -> Unit,
    onButtonClicked1: (Int) -> Unit, // 스튜디오 홈 버튼 클릭
    onButtonClicked2: (Int, Int, Int) -> Unit, // 두 번째 버튼 클릭

) {

    // 토큰 추출
    val token = tokenManager.getAccessToken()
    // context
    val context = LocalContext.current
    // 사용자 예약 내역 조회
    val userBookList = viewModel.userBookList.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadUserBookList(token = token)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer) // 클린업
        }
    }

    Scaffold(
        topBar = {
            TopAppBarComponent(
                title = "예약일정",
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
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.padding(innerPadding)
        ) {

            items(userBookList.value) { userBook ->

                // 버튼 상태 표시 여부
                val isButtonShow: Boolean = userBook.status != "예약취소"
                // 값 생성
                val uiValues = viewModel.makeValue(state = userBook.status)
                // 예약상태에 따른 chip text 색상
                val chipTextColor = uiValues.first
                // 예약상태에 따른 chip container 색상
                val chipContainerColor = uiValues.second
                // 버튼 label 텍스트
                val buttonLabelText = uiValues.third

                BookingScheduleItemComponent(
                    studioName = userBook.studioName,
                    studioImage = userBook.studioImage,
                    statusLabel = userBook.status,
                    createDate = userBook.createDate,
                    createTime = userBook.createTime,
                    chipTextColor = chipTextColor,
                    chipContainerColor = chipContainerColor,
                    chipBorderColor = chipContainerColor,
                    showButton = isButtonShow,
                    buttonLabel2 = buttonLabelText,
                    onButtonClicked1 = {
                        val studioId = userBook.studioId
                        // 스튜디오 상세 화면으로 이동
                        onButtonClicked1(studioId)
                    },
                    onButtonClicked2 = {
                        // 각 상태에 맞는 로직 수행
                        // 1: 예약일정 변경 화면으로 이동
                        // 2: 화면 이동 막고 Toast 띄워줌
                        // 3: 리뷰쓰기 화면으로 이동
                        val logicNumber = clickLogic(state = userBook.status)
                        if (logicNumber == 2){
                            Toast.makeText(context, "촬영 완료 후 리뷰쓰기가 가능합니다.", Toast.LENGTH_LONG).show()
                        } else {
                            // 화면 이동
                            onButtonClicked2(logicNumber, userBook.studioId, userBook.reservationId)
                        }
                    }
                )
            }
        }
    }
}


// 클릭 로직 결정
private fun clickLogic(state: String): Int{
    return when(state){
        "예약접수" -> 1
        "예약확정" -> 2
        "촬영완료" -> 3
        else -> 0
    }
}