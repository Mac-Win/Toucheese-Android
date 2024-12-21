package com.toucheese.app.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.toucheese.app.ui.theme.ToucheeseAppTheme

@Composable
fun TimeSlotButtonComponent(
    times: List<String>,
    selectedTime: String,
//    isPast: Boolean,
    modifier: Modifier,
    onTimeClick: (String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.heightIn(min = 50.dp, max = 200.dp)
    ) {
        items(times) { time ->
            val isSelected = time == selectedTime

            SuggestionChip(
                enabled = true,
                border = BorderStroke(1.dp, if (isSelected) MaterialTheme.colorScheme.primary else Color(0xFFD9D9D9)),
                label = {
                    Text(
                        text = time,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                shape = RoundedCornerShape(8.dp),
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                    labelColor = Color(0xFF1F1F1F),
                ),
                modifier = Modifier
                    .width(96.dp)
                    .height(40.dp),
                onClick = {},
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimeSlotButtonPreview() {
    val sampleTimes1 = listOf("10:00", "11:00", "12:00", "14:30", "15:30", "16:30")
    val sampleTimes2 = listOf("10:00", "11:00", "14:30") // 3개
    val sampleTimes3 = listOf("10:00", "11:00") // 2개
    val sampleTimes4 = listOf("10:00") // 1개
    val selectedTime = "12:00" // 선택된 시간 예시

    ToucheeseAppTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 6개
                TimeSlotButtonComponent(
                    times = sampleTimes1,
                    selectedTime = selectedTime,
                    onTimeClick = { time -> println("Selected time: $time") },
                    modifier = Modifier.fillMaxWidth()
                )
                // 3개
                TimeSlotButtonComponent(
                    times = sampleTimes2,
                    selectedTime = selectedTime,
                    onTimeClick = { time -> println("Selected time: $time") },
                    modifier = Modifier.fillMaxWidth()
                )
                // 2개
                TimeSlotButtonComponent(
                    times = sampleTimes3,
                    selectedTime = selectedTime,
                    onTimeClick = { time -> println("Selected time: $time") },
                    modifier = Modifier.fillMaxWidth()
                )
                // 1개
                TimeSlotButtonComponent(
                    times = sampleTimes4,
                    selectedTime = selectedTime,
                    onTimeClick = { time -> println("Selected time: $time") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}