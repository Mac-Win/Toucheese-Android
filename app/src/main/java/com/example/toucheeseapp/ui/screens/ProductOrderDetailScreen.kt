package com.example.toucheeseapp.ui.screens

import android.content.res.Resources
import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.Bitmap
import com.example.toucheeseapp.ui.components.AppBarImageComponent
import com.example.toucheeseapp.ui.components.DatePickComponent
import com.example.toucheeseapp.ui.components.ProductOrderOptionComponent
import com.example.toucheeseapp.ui.theme.ToucheeseAppTheme

@Composable
fun ProductOrderDetailScreen() {

    var totalPrice by remember { mutableStateOf(15000) }

    ToucheeseAppTheme {
        Scaffold(
            topBar = {
                AppBarImageComponent(
                    productName = "증명사진", // 임시 데이터
                    productInfo = "신원 확인이 주된 목적의 사진입니다.\n" +
                            "주로 공식 문서 및 신분증에 사용되는 사진입니다", // 임시 데이터
                    productImageTargetWidth = 300,
                    productImageTargetHeight = 450,
                    onBackButtonClicked = { /* 뒤로가기 */ },
                )
            },
            bottomBar = {
                // 주문 버튼
                BottomAppBar(
                    modifier = Modifier.background(Color.White)
                ) {
                    Button(
                        onClick = { /* 주문 클릭 시 동작 */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFFFFC000))
                    ) {
                        Text(
                            text = "선택 상품 주문 (₩$totalPrice)",
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                }
            },

            ) {
            LazyColumn(
                modifier = Modifier.padding(it)
            ) {
                item {
                    // 가격 & 옵션
                    ProductOrderOptionComponent(
                        productNumOfPeople = 1, //
                        productNumOfPeoplePrice = 75000,
                        productOptions = listOf(
                            Option("보정 사진 추가", 30000),
                            Option("원본 전체 받기", 10000),
                            Option("액자 프린팅", 15000)
                        ),
                        numOfPeople = 1,
                        onDecreaseClicked = {},
                        onIncreaseClicked = {},
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    // 촬영날짜
                    DatePickComponent(
                        date = "2024-11-28",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        onValueChanged = { },
                    )
                }
            }

        }
    }

}

fun resizeImage(resources: Resources, resId: Int, targetWidth: Int, targetHeight: Int): Bitmap {
    // 이미지 파일을 Bitmap으로 불러옴
    val originalBitmap = BitmapFactory.decodeResource(resources, resId)
    // 원본 Bitmap을 대상 크기로 리사이징
    return Bitmap.createScaledBitmap(originalBitmap, targetWidth, targetHeight, true)
}

// 임시 옵션 데이터
data class Option(
    val optionName: String,
    val optionPrice: Int
)
/*
onDatePicked: () -> Unit // 날짜 선택 시
 */