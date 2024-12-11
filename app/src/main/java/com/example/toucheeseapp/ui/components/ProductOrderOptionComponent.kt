package com.example.toucheeseapp.ui.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.toucheeseapp.R
import com.example.toucheeseapp.data.model.carts_list.AddOption

@Composable
fun ProductOrderOptionComponent(
    productNumOfPeople: Int, // 상품 기준 인원
    productNumOfPeoplePrice: Int, // 상품 기준(대표) 가격
    productOptions: List<AddOption>, // 추가 구매 옵션
    numOfPeople: Int, // 화면에 표시되는 인원 수
    reviewCount: Int, // 리뷰 갯수
    isOverFlow: Boolean, // 기준인원보다 선택된 인원수가 넘었는지 여부 (true: 넘음)
    isOnlyOne: Boolean, // 기준 인원이 1명인지 여부
    selectedOption: Set<Int>,
    modifier: Modifier = Modifier,
    onDecreaseClicked: () -> Unit,
    onIncreaseClicked: () -> Unit,
    onReviewButtonClicked: () -> Unit,
    onOptionClicked: (Int) -> Unit, // 옵션 클릭 시 동작
    selectedOptionChanged: (Int) -> Unit,
    showReviewButton: Boolean = true // 리뷰 버튼이 필요없는 화면에서 제거하기 위함
) {

    Column(modifier = modifier) {

        // 가격 및 인원
        PriceSection(
            productNumOfPeople = productNumOfPeople,
            productNumOfPeoplePrice = productNumOfPeoplePrice,
            numOfPeople = numOfPeople,
            reviewCount = reviewCount,
            isOverFlow = isOverFlow,
            isOnlyOne = isOnlyOne,
            onDecreaseClicked = onDecreaseClicked,
            onIncreaseClicked = onIncreaseClicked,
            onReviewButtonClicked = onReviewButtonClicked,
            showReviewButton = showReviewButton
            )

        // 추가 구매 옵션
        AdditionalOptions(
            productOptions = productOptions,
            isOverFlow = isOverFlow,
            selectedOption = selectedOption,
            modifier = Modifier.padding(horizontal = 16.dp),
            onOptionClicked = onOptionClicked,
            selectedOptionChanged = selectedOptionChanged
        )
    }
}

@Composable
private fun PriceSection(
    productNumOfPeople: Int,
    productNumOfPeoplePrice: Int,
    numOfPeople: Int, // 참여 인원
    reviewCount: Int,
    isOverFlow: Boolean,
    isOnlyOne: Boolean, // 기준 인원이 1명인지 여부
    modifier: Modifier = Modifier,
    onDecreaseClicked: () -> Unit,
    onIncreaseClicked: () -> Unit,
    onReviewButtonClicked: () -> Unit,
    showReviewButton: Boolean = true
) {
    Column(
        modifier = modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
    ) {
        // 리뷰 보러가기
        if (showReviewButton) {
            TextButton(
                onClick = onReviewButtonClicked,
            ) {
                Text(
                    text= "리뷰 ${reviewCount}개 보러가기 >",
                    fontSize = 12.sp
                )
            }
        }

        // 가격
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "가격",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "${productNumOfPeople}인 기준",
                color = if (isOverFlow) Color.Red else Color.Gray,
                fontSize = 8.sp,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                text = "${productNumOfPeoplePrice / 1000},000원",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,

                )
        }
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 4.dp),
            thickness = DividerDefaults.Thickness,
            color = Color.Gray
        )

        // 인원
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Toast 메시지를 위한 context 호출
            val context = LocalContext.current

            Text(
                text = "인원",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.weight(1f))

            // minus
            IconButton(
                onClick = {
                    if (!isOnlyOne){
                        // 기준 인원이 한 명이 아닌 경우에만 작동한다
                        onDecreaseClicked()
                    } else {
                        // 기준 인원이 1명인 경우 Toast 메시지를 띄워준다
                        Toast.makeText(context, "1명만 예약이 가능한 상품입니다.", Toast.LENGTH_SHORT).show()
                    }
                },
            ) {
                Icon(
                    painter = painterResource(R.drawable.minus_icon),
                    contentDescription = "Decrease",
                )
            }
            Text(
                text = "${numOfPeople}명",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
            )

            // plus
            IconButton(
                onClick = {
                    if (!isOnlyOne){
                        // 기준 인원이 1명이 아닌 경우에만 작동한다
                        onIncreaseClicked()
                    } else {
                        // 기준 인원이 1명인 경우 Toast 메시지를 띄워준다
                        Toast.makeText(context, "1명만 예약이 가능한 상품입니다.", Toast.LENGTH_SHORT).show()
                    }
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Increase",
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier.padding(top = 4.dp),
            thickness = DividerDefaults.Thickness,
            color = Color.Gray
        )
    }
}

@Composable
private fun AdditionalOptions(
    selectedOption: Set<Int>,
    productOptions: List<AddOption>,
    isOverFlow: Boolean,
    modifier: Modifier = Modifier,
    onOptionClicked: (Int) -> Unit,
    selectedOptionChanged: (Int) -> Unit,
) {

    Column(
        modifier = modifier
    ) {
        if (isOverFlow){
            // 기준 인원 공지
            Text(
                text = "현재 공지된 가격은 기준인원당 가격입니다. 기준인원 초과시 추가 금액이 부과될 수 있습니다.",
                fontSize = 8.sp,
                color = Color.Red,
                modifier = Modifier
                    .align(Alignment.End)
                    .alpha(0.7f),
            )
        }

        Text(
            text = "추가 구매",
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
        )

        // 추가 구매 옵션
        productOptions.forEachIndexed { index, option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedOption.contains(option.id),
                    colors = RadioButtonColors(
                        selectedColor = Color(0xFFFFE085),
                        unselectedColor = Color(0xFFFFE085),
                        disabledSelectedColor = Color(0xFFFFFFFF),
                        disabledUnselectedColor = Color(0xFFFFFFFF),
                    ),
                    modifier = Modifier.padding(2.dp),
                    onClick = {
                        // 금액 변경
                        onOptionClicked(
                            if (selectedOption.contains(option.id)) -option.price else option.price
                        )
                        // 새로운 Set 객체를 생성하여 상태 변경
                        selectedOptionChanged(option.id)
                    }
                )
                // 옵션명
                Text(
                    text = option.name,
                    fontSize = 16.sp,
                )

                Spacer(modifier = Modifier.weight(1f))

                // 옵션 가격
                Text(
                    text = "${option.price / 1000},000원",
                    fontSize = 16.sp,

                    )
            }
        }
    }
}
