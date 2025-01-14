package com.toucheese.app.ui.screens.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.toucheese.app.ui.components.textfield.TextFieldOutlinedComponent
import com.toucheese.app.ui.components.topbar.TopAppBarComponent
import com.toucheese.app.ui.viewmodel.LoginViewModel

@Composable
fun SignUpAdditionalInfoScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onClickLeadingIcon: () -> Unit,
    onSignUpButtonClicked: () -> Unit,
) {
    // 인증번호 요청 여부
    var isCertificateRequested: Boolean = false
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
                    onClick = onSignUpButtonClicked,
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
                    text = "본인 확인을 위한\n이름과 연락처를 입력해주세요"
                )
            }


            // 이름
            item {
                Text(text = "이메일")

                // 공간
                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                // 입력
                TextFieldOutlinedComponent(
                    textFieldValue = "",
                    leadingIcon = Icons.Default.MailOutline,
                    placeholder = "이름을 입력해주세요",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Email,
                    ),
                    onValueChanged = {
                        // 값이 변할 때 작동
                    },
                )
            }

            // 연락처
            item {
                Text(text = "연락처")

                // 공간
                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Row {
                    // 연락처
                    TextFieldOutlinedComponent(
                        textFieldValue = "",
                        leadingIcon = Icons.Default.Lock,
                        placeholder = "숫자만 입력해주세요",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Password,
                        ),
                        onValueChanged = {
                            // 값이 변할 때 작동
                        },
                    )

                    // 공간
                    Spacer(modifier = Modifier.width(8.dp))

                    // 인증번호
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF434343),
                            contentColor = Color.White,
                        ),
                        onClick = {
                            // 인증번호 요청
                            isCertificateRequested = true
                        }
                    ) {
                        Text(
                            text = "인증번호",
                        )
                    }
                }

                // 공간
                Spacer(modifier = Modifier.height(8.dp))

                // 인증번호
                if (isCertificateRequested){
                    Row {
                        // 인증번호
                        TextFieldOutlinedComponent(
                            textFieldValue = "",
                            leadingIcon = Icons.Default.Lock,
                            placeholder = "인증번호를 입력해주세요",
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Password,
                            ),
                            onValueChanged = {
                                // 값이 변할 때 작동
                            },
                        )

                        // 공간
                        Spacer(modifier = Modifier.width(8.dp))

                        // 인증번호 확인
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                            onClick = {
                                // 인증번호 확인

                            }
                        ) {
                            Text(
                                text = "인증하기",
                            )
                        }
                    }
                }
            }
        }
    }
}