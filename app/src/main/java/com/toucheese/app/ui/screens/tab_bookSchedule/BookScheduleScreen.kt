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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
    onButtonClicked2: (Int) -> Unit, // 두 번째 버튼 클릭

) {

    // 토큰 추출
    val token = tokenManager.getAccessToken()
    // context
    val context = LocalContext.current
    // 사용자 예약 내역 조회
    val userBookList = viewModel.userBookList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadUserBookList(token = token)
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
                Log.d("BookSchedule", "예약취소? = ${isButtonShow}")
                Log.d("BookSchedule", "예약상태 = ${userBook.status}")
                // 예약상태에 따른 chip text 색상
                val chipTextColor = makeValue(state = userBook.status).first
                // 예약상태에 따른 chip container 색상
                val chipContainerColor = makeValue(state = userBook.status).second
                // 버튼 label 텍스트
                val buttonLabelText = makeValue(state = userBook.status).third

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
                            onButtonClicked2(logicNumber)
                        }
                    }
                )
            }
        }
    }
}
// 첫 번째 반환값 : Text Color
// 두 번째 반환값 : Container Color
private fun makeValue(state: String): Triple<Color, Color, String>{
    return when(state) {
        "예약접수" -> Triple(Color(0xFF595959), Color(0xFFF0F0F0), "예약일정 변경")
        "예약확정" -> Triple(Color(0xFF1F1F1F), Color(0xFFFFD129), "리뷰쓰기")
        "촬영완료" -> Triple(Color(0xFF2B89FE), Color(0xFFF0F0F0), "예약일정 변경")
        "예약취소" -> Triple(Color(0xFFEF4444), Color(0xFFF0F0F0), "")
        else -> Triple(Color.White, Color.White, "")
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