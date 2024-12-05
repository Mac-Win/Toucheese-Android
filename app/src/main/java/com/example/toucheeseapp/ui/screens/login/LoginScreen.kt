package com.example.toucheeseapp.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.toucheeseapp.R
import com.example.toucheeseapp.ui.components.textbutton.TextButtonCheckboxComponent
import com.example.toucheeseapp.ui.components.textfield.TextFieldOutlinedComponent
import com.example.toucheeseapp.ui.theme.Shapes

@Composable
fun LoginScreen(modifier: Modifier = Modifier, onLoginClicked: () -> Unit) {
    // id 정보
    val (textFieldId, setId) = remember { mutableStateOf("") }
    // 비밀번호 정보
    val (textFieldPw, setPw) = remember { mutableStateOf("") }
    // 자동 로그인 여부
    val (autoLogin, setLogin) = remember { mutableStateOf(false) }


    Scaffold(
        topBar = {
            Image(
                painter = painterResource(R.drawable.toucheese_kr_logo),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 100.dp,
                        vertical = 150.dp
                    )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Id 입력
            TextFieldOutlinedComponent(
                textFieldValue= textFieldId,
                onValueChanged = setId,
                placeholder = "email@naver.com",
                leadingIcon = Icons.Default.Person,
                keyboardOptions = KeyboardOptions().copy(
                    keyboardType = KeyboardType.Email, // 이메일 형식
                    imeAction = ImeAction.Next, // 확인 버튼 클릭 시 다음으로 포커스 이동
                ),
                modifier = Modifier
                    .height(IntrinsicSize.Max)
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            // 비밀번호 입력
            TextFieldOutlinedComponent(
                textFieldValue= textFieldPw,
                onValueChanged = setPw,
                placeholder = "비밀번호 입력",
                leadingIcon = Icons.Default.Lock,
                keyboardOptions = KeyboardOptions().copy(
                    keyboardType = KeyboardType.Password, // 비밀번호 형식
                    imeAction = ImeAction.Done, // 확인 버튼 클릭 시 포커스 제거
                ),
                modifier = Modifier
                    .height(IntrinsicSize.Max)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            // 자동 로그인, 회원 가입, 아이디 및 비번 찾기


            // 로그인 버튼
            Button(
                onClick = onLoginClicked,
                modifier = Modifier.padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFCC00),
                    contentColor = Color.White
                ),
                shape = Shapes.small,
                elevation = ButtonDefaults.elevatedButtonElevation(8.dp)
            ) {
                Text(
                    text = "Login",
                    modifier = Modifier.padding(8.dp)
                )
            }


        }

    }
}