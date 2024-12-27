package com.toucheese.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toucheese.app.R

@Composable
fun MyInfoProflieComponent(
    modifier: Modifier = Modifier
){

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){
        // 프로필 이미지
        Image(
            painter = painterResource(R.drawable.profile),
            modifier = Modifier
                .size(68.dp),
            contentScale = ContentScale.Crop,
            contentDescription = "기본 프로필 이미지"
        )
        Spacer(modifier = Modifier.width(8.dp))

        Column (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // 프로필 내용
            Text(
                text = "홍길동",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = "likelion@likelion.net",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Text(
                text = "010-0000-0000",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

@Preview
@Composable
private fun MyInfoProfileComponent(){
    MyInfoProflieComponent()
}