package com.toucheese.app.ui.screens.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.toucheese.app.R
import com.toucheese.app.data.token_manager.TokenManager
import com.toucheese.app.ui.components.SocialLoginButton
import com.toucheese.app.ui.components.textfield.TextFieldOutlinedComponent
import com.toucheese.app.ui.viewmodel.LoginViewModel
import com.toucheese.app.ui.viewmodel.TAG
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onLoginClicked: (Int, String, Boolean) -> Unit
) {
    // id 정보
    val (textFieldId, setId) = remember { mutableStateOf("") }
    // 비밀번호 정보
    val (textFieldPw, setPw) = remember { mutableStateOf("") }
    // 자동 로그인 여부
    val (autoLogin, setLogin) = remember { mutableStateOf(false) }
    // Coroutine
    val coroutine = rememberCoroutineScope()
    // Context
    val context = LocalContext.current
    // SnackBar
    val hostState = remember { SnackbarHostState() }
    // 키보드
    val imeController = LocalSoftwareKeyboardController.current


    Scaffold(
        containerColor = Color.White,
        snackbarHost = { SnackbarHost(hostState = hostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .padding(top = 128.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        )
        {
            // 로고 및 설명 텍스트
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    painter = painterResource(R.drawable.toucheeselogo),
                    contentDescription = "Toucheese Logo",
                    modifier = Modifier
                        .height(24.dp)
                        .width(180.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "스튜디오 고민은 그만!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "터치즈에 로그인하고 스튜디오를 한 눈에 살펴보세요.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(24.dp)) // 콘텐츠와 로고 사이 간격 조정

            // Id 입력
            TextFieldOutlinedComponent(
                textFieldValue = textFieldId,
                onValueChanged = setId,
                placeholder = "이메일을 입력해주세요",
                leadingIcon = Icons.Default.Person,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            // 비밀번호 입력
            TextFieldOutlinedComponent(
                textFieldValue = textFieldPw,
                onValueChanged = setPw,
                placeholder = "비밀번호를 입력해주세요",
                leadingIcon = Icons.Default.Lock,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                visualTransformation = PasswordVisualTransformation()
            )

            // 자동 로그인, 회원 가입, 아이디 및 비번 찾기
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 자동 로그인 체크박스
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = autoLogin,
                        onCheckedChange = { setLogin(it) },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.primary,
                            uncheckedColor = Color.Gray
                        )
                    )
                    Text(
                        text = "자동 로그인",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // 아이디/비밀번호 찾기 버튼
                TextButton(onClick = {
                    Toast.makeText(context, "아직 구현되지 않은 기능입니다.", Toast.LENGTH_SHORT).show()
                }) {
                    Text(
                        text = "회원가입 / ID PASSWORD 찾기",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) // 로그인 버튼과 아래 요소 간 간격

            // 로그인 버튼
            Button(
                onClick = {
                    coroutine.launch {
                        // 로그인 요청
                        viewModel.requestLogin(
                            tokenManager = TokenManager(context),
                            email = textFieldId,
                            password = textFieldPw
                        )
                        // 로그인 여부 확인
                        val result = viewModel.isLoggedIn(tokenManager = TokenManager(context))
                        Log.d(TAG, "result = $result")
                        if (result) {
                            Log.d(TAG, "result = $result")
                            // 로그인 성공 시 화면 전환
                            onLoginClicked(
                                viewModel.memberId.value,
                                viewModel.memberName.value,
                                result
                            )
                        } else {
                            // 로그인 실패 시 Snackbar 표시
                            hostState.showSnackbar("아이디 또는 비밀번호를 확인해주세요.")
                        }
                    }
                    // 키보드 내리기
                    imeController?.hide()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White // 버튼 텍스트 색상을 대비되게 조정
                ),
                shape = RoundedCornerShape(8.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(8.dp)
            ) {
                Text(
                    text = "로그인",
                    modifier = Modifier.padding(vertical = 8.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            Row (
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Divider(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp),
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "SNS 간편 로그인",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Divider(
                    modifier = Modifier
                        .weight(1f)
                        .height(1.dp),
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SocialLoginButton(
                    backgroundColor = Color(0xFFFEE500), // 카카오 노란색
                    icon = painterResource(R.drawable.kakaologo),
                    text = "카카오 로그인",
                    textColor = Color.Black,
                    onClick = { /* 카카오 로그인 로직 */ }
                )

                Spacer(modifier = Modifier.height(8.dp))

                SocialLoginButton(
                    backgroundColor = Color(0xFF03C75A), // 네이버 초록색
                    icon = painterResource(R.drawable.naverlogo),
                    text = "네이버 로그인",
                    textColor = Color.White,
                    onClick = { /* 네이버 로그인 로직 */ }
                )
            }
        }
    }
}
