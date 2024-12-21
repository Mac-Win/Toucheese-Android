package com.toucheese.app.ui.screen.login

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.toucheese.app.data.token_manager.TokenManager
import com.toucheese.app.ui.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun AdditionalInfoScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    val context = LocalContext.current
    val tokenManager = TokenManager(context)
    val coroutineScope = rememberCoroutineScope()
    val memberName by viewModel.memberName.collectAsState()

    LaunchedEffect(memberName) {
        // Mock 처리: memberName이 업데이트되면 추가 정보 입력 성공으로 간주하고 홈으로 이동
        if (memberName.isNotEmpty() && memberName != "KakaoUser") {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Column {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("이름") }
        )
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("전화번호") }
        )
        Button(onClick = {
            coroutineScope.launch {
                viewModel.submitAdditionalInfo(tokenManager, name, phoneNumber)
            }
        }) {
            Text("정보 제출")
        }
    }
}