package com.toucheese.app.ui.screens.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.toucheese.app.ui.components.textfield.TextFieldOutlinedComponent
import com.toucheese.app.ui.components.topbar.TopAppBarComponent
import com.toucheese.app.ui.theme.ToucheeseAppTheme
import com.toucheese.app.ui.viewmodel.SignUpViewModel

@Composable
fun SignUpBasicInfoScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onClickLeadingIcon: () -> Unit,
    onNextButtonClicked: () -> Unit,
) {
    // email 상태
    val emailState by viewModel.emailState.collectAsState()
    // email 유효성
    val isValidateEmail by viewModel.isValidateEmail.collectAsState()

    Scaffold(
        topBar = {
            TopAppBarComponent(
                title = "회원가입",
                showLeadingIcon = true,
                showTrailingIcon = false,
                leadingIcon = Icons.AutoMirrored.Default.KeyboardArrowLeft, // 뒤로가기
                onClickLeadingIcon = onClickLeadingIcon, // 뒤로가기 클릭 시 로직
            )
        },
        bottomBar = {
            // 다음 버튼
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),

            ) {
                Button(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onNextButtonClicked,
                ){
                    Text(
                        text = "다음"
                    )

                }
            }
        },
        modifier = Modifier.safeDrawingPadding(),
    ) { innerPadding ->

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = modifier
                .padding(innerPadding)
                .padding(16.dp),
        ) {
            // 회원가입 안내 문구
            item{
                Text(
                    text = "로그인을 위한\n이메일과 비밀번호를 입력해주세요"
                )
            }


            // 이메일
            item {
                Text(text = "이메일")

                // 공간
                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                // 입력
                TextFieldOutlinedComponent(
                    textFieldValue = emailState,
                    placeholder = "이메일을 입력해주세요",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Email,
                    ),
                    errorMessage = "이메일 형식을 맞춰주세요 (Ex. toucheese@email.com)",
                    showError = !isValidateEmail,
                    showLeadingIcon = false,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChanged = { email ->
                        // 값 저장 & 유효성 검사
                        viewModel.setEmail(email)
                    },
                )
            }

            // 비밀번호
            item {
                Text(text = "비밀번호")

                // 공간
                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                // 입력
                TextFieldOutlinedComponent(
                    textFieldValue = "",
                    placeholder = "비밀번호를 입력해주세요",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Password,
                    ),
                    showLeadingIcon = false,
                    onValueChanged = {
                        // 값이 변할 때 작동
                    },
                )
            }

            // 비밀번호 확인
            item {
                Text(text = "비밀번호 확인")

                // 공간
                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                // 입력
                TextFieldOutlinedComponent(
                    textFieldValue = "",
                    placeholder = "비밀번호를 한 번 더 입력해주세요",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Password,
                    ),
                    showLeadingIcon = false,
                    onValueChanged = {
                        // 값이 변할 때 작동
                    },
                )
            }
        }

    }
}


@Preview
@Composable
private fun SignUpBasicInfoScreenPreview() {
    ToucheeseAppTheme {
        SignUpBasicInfoScreen(
            onClickLeadingIcon = { },
            onNextButtonClicked = { },
        )
    }
}