package com.example.toucheeseapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
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
import com.example.toucheeseapp.R

@Composable
fun bookingScheduleItemComponent(modifier: Modifier = Modifier){
    Card (
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
            ){
            Image(
                painter = painterResource(R.drawable.profileimage),
                contentDescription = "Studio Profile Image",
                modifier = Modifier.align(Alignment.CenterVertically)
                    .size(52.dp)
                    .clip(RoundedCornerShape(20.dp)),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(8.dp)){
                Text(
                    text = "두콩스튜디오",
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
                        text = "2024-12-08 13:00",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))

                SuggestionChip(
                    onClick = {},
                    enabled = false,
                    label = { Text("예약대기") },
                    modifier = Modifier.align(Alignment.Top),
                    shape = RoundedCornerShape(8.dp)
                )
        }
    }
}


@Preview
@Composable
fun bookingScheduleItemPreview(){
    Surface {
        bookingScheduleItemComponent()
    }

}