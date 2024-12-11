package com.example.toucheeseapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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

@Composable
fun CartItemComponent(
    cartItem: CartListResponseItem,
    onDeleteClick: (Int) -> Unit,
    onOptionChangeClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier
            .background(Color(0xFFFFFCF5))) {
            // 스튜디오 정보와 삭제 버튼
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFF2CC))
                    .padding(8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Image(
                        painter = rememberAsyncImagePainter(model = cartItem.studioImage),
                        contentDescription = "Studio Profile Image",
                        modifier = Modifier
                            .size(52.dp)
                            .clip(RoundedCornerShape(20.dp)), // 원형 모양
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = cartItem.studioName,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

                // 삭제 버튼
                IconButton(onClick = { onDeleteClick(cartItem.cartId) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Item",
                        tint = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 상품 이미지와 상품 정보
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // 상품 이미지
                Image(
                    painter = rememberAsyncImagePainter(model = cartItem.productImage),
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .size(140.dp)
                        .clip(RoundedCornerShape(8.dp)), // 이미지 모서리 둥글게
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.weight(0.1f))

                // 상품 정보
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                    modifier = Modifier.weight(1f)
                        .padding(8.dp)){

                    Text(
                        text = cartItem.productName,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Row(verticalAlignment = Alignment.CenterVertically){
                        Text("예약 인원: ${cartItem.personnel}명", style = MaterialTheme.typography.bodyMedium)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("예약 날짜: ${cartItem.reservationDate}", style = MaterialTheme.typography.bodyMedium)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("예약 시간: ${cartItem.reservationTime}", style = MaterialTheme.typography.bodyMedium)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("전체 가격: ${cartItem.totalPrice}", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 옵션 변경 버튼
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = {onOptionChangeClick(cartItem.cartId)}) {
                    Text(
                        text = "옵션변경",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CartItemPreview() {
//    val sampleItem = CartListItem(
//        studioName = "공원스튜디오",
//        personnel = 1,
//        productName = "증명사진",
//        reservationDate = "2024-12-10",
//        reservationTime = ReservationTime(14, 0, 0, 0),
//        totalPrice = 105000,
//        addOptions = emptyList(),
//        cartId = 1,
//        productImageUrl = "https://via.placeholder.com/140",
//        studioImageUrl = "https://via.placeholder.com/52"
//    )
//
//    CartItemComponent(
//        cartItem = sampleItem,
//        onDeleteClick = { cartId -> println("Delete cartId: $cartId") },
//        onOptionChangeClick = { cartId -> println("Change Option cartId: $cartId") }
//    )
}