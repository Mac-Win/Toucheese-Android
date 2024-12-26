package com.toucheese.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.toucheese.app.R

@Composable
fun CartItemComponent(
    isCardClicked: Boolean,
    studioName: String,
    productName: String,
    productImage: String,
    createDate: String,
    createTime: String,
    personal: Int,
    totalPrice: Int,
    modifier: Modifier = Modifier,
    onCardClicked: () -> Unit,
    onOptionChangeClicked: () -> Unit,
    ) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(12.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(12.dp)
        ) {
            // 이미지 및 주문 정보
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                // 이미지
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1.25f)
                ) {
                    AsyncImage(
                        model = productImage,
                        contentDescription = "product Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // 주문 정보
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.weight(3f)
                ) {
                    // 스튜디오 이름 및 체크 박스
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // 스튜디오 이름
                        Text(
                            text = studioName,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        // 체크박스 버튼
                        SquareRadioButton(
                            selected = isCardClicked,
                            selectedColor = MaterialTheme.colorScheme.primary,
                            unselectedColor = Color.Transparent,
                            borderColor = if (isCardClicked) Color.Black else Color(0xFFBFBFBF),
                            onClick = onCardClicked,
                        )
                    }

                    // 상품명
                    Text(
                        text = productName,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    // 예약일자
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.calendar_36px),
                            contentDescription = "예약일자",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))

                        Text(
                            text = "예약일자 : $createDate $createTime",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    // 예약인원
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "예약인원",
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(2.dp))

                        Text(
                            text = "예약인원 : ${personal}명",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    // 총 가격
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "총 ${totalPrice / 1000},000원",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                }

            }

            // 옵션 변경 버튼
            Button(
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color(0xFFF0F0F0)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFAFAFA),
                    contentColor = Color(0xFF141414)
                ),
                modifier = Modifier.fillMaxWidth(),
                onClick = onOptionChangeClicked
            ) {
                Text(
                    text = "옵션 변경",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }


    }
}

@Preview
@Composable
private fun CartItemPreview() {
    MaterialTheme {
        CartItemComponent(
            isCardClicked = true,
            studioName = "어디 스튜디오",
            productName = "프로필 사진",
            productImage = "https://i.imgur.com/EEVgYuZ.jpeg",
            personal = 3,
            createDate = "2024-12-24",
            createTime = "14:30",
            totalPrice = 75000,
            onCardClicked = {},
            onOptionChangeClicked = {},
        )
    }
}