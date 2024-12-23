package com.toucheese.app.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.toucheese.app.data.token_manager.TokenManager
import com.toucheese.app.ui.viewmodel.LoginViewModel
import com.toucheese.app.ui.viewmodel.UpdateInfoUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdditionalInfoScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavController,
    tokenManager: TokenManager
) {
    // 사용자 입력 상태
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    // Coroutine
    val coroutine = rememberCoroutineScope()

    // UI 상태 관찰
    val updateInfoState by viewModel.updateInfoState.collectAsState()

    // SnackBar
    val hostState = remember { SnackbarHostState() }

    // UI 업데이트에 따른 반응
    LaunchedEffect(updateInfoState) {
        when (updateInfoState) {
            is UpdateInfoUiState.Success -> {
                // 추가 정보 업데이트 성공 시 메인 화면으로 이동
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }
            is UpdateInfoUiState.Error -> {
                val msg = (updateInfoState as UpdateInfoUiState.Error).msg
                coroutine.launch {
                    hostState.showSnackbar("정보 업데이트 오류: $msg")
                }
            }
            else -> {}
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = hostState) },
        topBar = {
            TopAppBar(
                title = { Text("추가 정보 입력") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 이름 입력 필드
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("이름") },
                placeholder = { Text("이름을 입력해주세요") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 전화번호 입력 필드
            OutlinedTextField(
                value = phone,
                onValueChange = {
                    // 입력값을 숫자로만 제한하고 최대 11자리로 제한
                    if (it.length <= 11 && it.all { char -> char.isDigit() }) {
                        phone = it
                    }
                },
                label = { Text("전화번호") },
                placeholder = { Text("전화번호를 입력해주세요 (11자리)") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // 전화번호 오류 메시지
            if (phone.isNotEmpty() && phone.length != 11) {
                Text(
                    text = "전화번호는 정확히 11자리여야 합니다.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }


            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    coroutine.launch {
                        viewModel.updateMemberInfo(
                            name = name,
                            phoneNumber = phone,
                            tokenManager = tokenManager
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = name.isNotBlank() && phone.length == 11,
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "확인")
            }
        }
    }
}