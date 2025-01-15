package com.toucheese.app.ui.screens.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.toucheese.app.ui.components.textfield.TextFieldOutlinedComponent
import com.toucheese.app.ui.components.topbar.TopAppBarComponent
import com.toucheese.app.ui.viewmodel.SignUpViewModel

@Composable
fun SignUpAdditionalInfoScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onClickLeadingIcon: () -> Unit,
    onSignUpButtonClicked: () -> Unit,
) {
    // 인증번호 요청 여부
    var isCertificateRequested by remember { mutableStateOf(false) }

    // 이름
    val nameState by viewModel.nameState.collectAsState()
    // 이름 유효성
    val isValidateName by viewModel.isValidateName.collectAsState()
    // 연락처
    val contactState by viewModel.contactState.collectAsState()
    // 연락처 유효성
    val isValidateContact by viewModel.isValidateContact.collectAsState()

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
                ) {
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
            item {
                Text(
                    text = "본인 확인을 위한\n이름과 연락처를 입력해주세요"
                )
            }


            // 이름
            item {
                Text(text = "이름")

                // 공간
                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                // 입력
                TextFieldOutlinedComponent(
                    textFieldValue = nameState,
                    placeholder = "이름을 입력해주세요",
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Text,
                    ),
                    errorMessage = "한글로 2자 이상 4자 이하의 이름을 작성해주세요",
                    showError = !isValidateName,
                    showLeadingIcon = false,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChanged = { name: String ->
                        // 값이 변할 때 작동
                        viewModel.setName(name)
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

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().height(intrinsicSize = IntrinsicSize.Min)
                ) {

                    // 연락처
                    TextFieldOutlinedComponent(
                        textFieldValue = contactState,
                        placeholder = "010-XXXX-XXXX",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Phone,
                        ),
                        modifier = Modifier.weight(1f),
                        onValueChanged = { contact: String ->
                            // 값이 변할 때 작동
                            viewModel.setContact(contact)
                        },
                    )

                    // 공간
                    Spacer(modifier = Modifier.width(8.dp))

                    // 인증번호
                    Button(
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF434343),
                            contentColor = Color.White,
                        ),
                        modifier = Modifier.fillMaxHeight(),
                        onClick = {
                            // 인증번호 요청
                            isCertificateRequested = true
                        }
                    ) {
                        Text(
                            text = if (isCertificateRequested) "다시요청" else "인증번호",
                        )
                    }
                }
            }

            if (isCertificateRequested) {
                item {
                    // 인증번호
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                    ) {
                        // 인증번호
                        TextFieldOutlinedComponent(
                            textFieldValue = "",
                            placeholder = "인증번호를 입력해주세요",
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Phone,
                            ),
                            showLeadingIcon = false,
                            modifier = Modifier.weight(1f),
                            onValueChanged = {
                                // 값이 변할 때 작동
                            },
                        )

                        // 공간
                        Spacer(modifier = Modifier.width(8.dp))

                        // 인증번호 확인
                        Button(
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                            modifier = Modifier.fillMaxHeight(),
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