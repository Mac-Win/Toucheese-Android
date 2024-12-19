package com.toucheese.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.toucheese.app.R
import com.toucheese.app.ui.theme.ToucheeseAppTheme

@Composable
fun BookingScheduleItemComponent(
    studioName: String, // 스튜디오 이름
    createDate: String, // 예약일자
    createTime: String, // 예약 시간
    studioImage: String, // 스튜디오 사진
    statusLabel: String, // 예약 상태
    buttonLabel1: String = "스튜디오 홈",
    buttonLabel2: String,
    chipContainerColor: Color,
    chipTextColor: Color,
    chipBorderColor: Color,
    showButton: Boolean = true,
    elevation: CardElevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
    modifier: Modifier = Modifier,
    onButtonClicked1: () -> Unit,
    onButtonClicked2: () -> Unit,
    ){
    Card (
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = elevation,
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(studioImage),
                    contentDescription = "Studio Profile Image",
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(40.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(8.dp)
                ) {
                    Text(
                        text = studioName,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Calender",
                            tint = Color.Gray
                        )
                        Text(
                            text = "${createDate} ${createTime}",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))

                SuggestionChip(
                    shape = RoundedCornerShape(6.dp),
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = chipContainerColor,
                        labelColor = chipTextColor,
                    ),
                    border = BorderStroke(1.dp, chipBorderColor),
                    onClick = {},
                    label = {
                        Text(
                            text = statusLabel,
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    modifier = Modifier.align(Alignment.Top),
                )
            }

            if (showButton) {
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background),
                        border = BorderStroke(1.dp, Color(0xFFF0F0F0)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.5f),
                        onClick = onButtonClicked1,
                    ) {
                        Text(
                            text = buttonLabel1,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }


                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color(0xFFF0F0F0)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.5f),
                        onClick = onButtonClicked2,
                    ) {
                        Text(
                            text = buttonLabel2,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}