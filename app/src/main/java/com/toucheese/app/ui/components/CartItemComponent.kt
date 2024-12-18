package com.toucheese.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.toucheese.app.data.model.home.carts_list.CartListResponseItem

@Composable
fun CartItemComponent(
    cartItem: com.toucheese.app.data.model.home.carts_list.CartListResponseItem,
    showDeleteIcon: Boolean = true,
    showOptionChangeButton: Boolean = true,
    modifier: Modifier = Modifier,
    onDeleteClick: (com.toucheese.app.data.model.home.carts_list.CartListResponseItem) -> Unit,
    onOptionChangeClick: (com.toucheese.app.data.model.home.carts_list.CartListResponseItem) -> Unit,

    ) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFCF5)
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        // 스튜디오 정보와 삭제 버튼
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFF2CC))
                .padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = cartItem.studioImage),
                contentDescription = "Studio Profile Image",
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = cartItem.studioName,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // 휴지통 아이콘을 표시할지 여부에 따라 조건부로 렌더링
            if (showDeleteIcon) {
                IconButton(onClick = { onDeleteClick(cartItem) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Item",
                        tint = Color.Gray
                    )
                }
            }
        }

        // 상품 이미지와 상품 정보
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            // 상품 이미지
            Image(
                painter = rememberAsyncImagePainter(model = cartItem.productImage),
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(140.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            // 상품 정보
            Box(
                modifier = Modifier
                    .align(Alignment.Top)
                    .fillMaxHeight()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .align(Alignment.TopStart)
                ) {
                    Text(
                        text = cartItem.productName,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "예약 인원: ${cartItem.personnel}명",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "예약 날짜: ${cartItem.reservationDate}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "예약 시간: ${cartItem.reservationTime}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "전체 가격: ${cartItem.totalPrice / 1000},000원",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                }
                // 옵션 변경
                if (showOptionChangeButton) {
                    Text(
                        text = "옵션변경",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .clickable { onOptionChangeClick(cartItem) }
                    )
                }
            }
        }
    }
}
