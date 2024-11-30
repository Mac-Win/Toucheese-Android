package com.example.toucheeseapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun AppBarImageComponent(
    productName: String, // 상품명
    productInfo: String, // 상품 상세 설명
    productImage: String, // 상품 이미지
    modifier: Modifier = Modifier,
    onBackButtonClicked:() -> Unit,
) {

    Box(
        modifier = Modifier
            .safeDrawingPadding()
            .background(Color(0xFFFFFCF5)),
        contentAlignment = Alignment.TopStart,
    ) {
        IconButton(
            modifier = Modifier.padding(16.dp),
            onClick = onBackButtonClicked
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }

        // Image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.33f) // 전체 높이의 1/3만 차지
                .safeDrawingPadding(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,


                ) {
                // 상품 이미지
                Column(
                    modifier= Modifier
                        .fillMaxWidth(0.33f)
                        .fillMaxHeight(0.7f)
                        .padding(8.dp),

                ) {
                    AsyncImage(
                        model = productImage,
                        contentDescription = "Photo",
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .border(BorderStroke(1.dp, Color.Black))
                    )
                }

                // 상품명
                Text(
                    text = productName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp),

                    )

                // 상품 상세설명
                Text(
                    text = productInfo,
                    textAlign = TextAlign.Center,
                    fontSize = 8.sp,
                    color = Color.Gray,
                    modifier = Modifier.width(200.dp)
                )
            }
        }
    }

}