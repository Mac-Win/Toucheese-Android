package com.toucheese.app.ui.screens.sign_up

import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.toucheese.app.R
import com.toucheese.app.ui.components.topbar.TopAppBarComponent
import com.toucheese.app.ui.viewmodel.LoginViewModel

@Composable
fun SignUpWelcomeScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onButtonClicked: () -> Unit,
) {
    val context = LocalContext.current
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding(),
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            // 축하 이미지
            Image(
                painter = painterResource(R.drawable.celebration),
                contentDescription = "Toucheese Celebration",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(80.dp)
                    .height(64.dp)
            )

            // 공간
            Spacer(modifier = Modifier.height(16.dp))

            // 환영 문구
            Text(
                text = "터치즈에 오신 것을 환영합니다!",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            // 공간
            Spacer(modifier = Modifier.height(8.dp))

            // 안내 문구
            Text(
                text = "가입이 완료됐습니다.\n터치즈에서 딱 맞는 스튜디오를 찾아보세요.",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            // 공간
            Spacer(modifier = Modifier.height(40.dp))

            // 버튼
            Button(
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.Black,
                ),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    Toast.makeText(context, "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show()

                    onButtonClicked()
                },
            ) {
                Text(
                    text = "스튜디오 둘러보기",
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }


}