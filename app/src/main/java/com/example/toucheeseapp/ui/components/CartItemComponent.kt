package com.example.toucheeseapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.toucheeseapp.data.model.carts_list.CartListResponseItem
import com.example.toucheeseapp.data.model.carts_list.ReservationTime
import com.example.toucheeseapp.data.model.carts_list.AddOption
import com.example.toucheeseapp.data.model.carts_list.SelectAddOption

@Composable
fun CartItemComponent(
    cartItem: CartListResponseItem,
    showDeleteIcon: Boolean = true,
    showOptionChangeButton: Boolean = true,
    modifier: Modifier = Modifier,
    onDeleteClick: (CartListResponseItem) -> Unit,
    onOptionChangeClick: (CartListResponseItem) -> Unit,

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
        Column {
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
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = cartItem.productName,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
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
                            "전체 가격: ${cartItem.totalPrice}",
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        if (showOptionChangeButton) {
                            Text(
                                text = "옵션변경",
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.clickable { onOptionChangeClick(cartItem) }
                            )
                        }
                    }
                }
            }
        }
    }
}
