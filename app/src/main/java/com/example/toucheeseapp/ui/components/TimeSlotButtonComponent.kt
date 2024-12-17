package com.example.toucheeseapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.toucheeseapp.ui.theme.ToucheeseAppTheme

@Composable
fun TimeSlotButtonComponent(
    modifier: Modifier,
    times: List<String>,
    selectedTime: String?,
    onTimeClick: (String) -> Unit
) {
    // 3개의 열로 나누기
    val rows = times.chunked(3)

    Column(modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        rows.forEach { rowTimes ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowTimes.forEach { time ->
                    val isSelected = time == selectedTime
                    Box(
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .size(width = 96.dp, height = 40.dp)
                            .background(
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.inverseOnSurface,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .clickable { onTimeClick(time) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = time,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                // 빈 공간을 채우기 위해 투명한 Box 추가
                val emptyCount = 3 - rowTimes.size
                repeat(emptyCount) {
                    Box(
                        modifier = Modifier
                            .size(width = 96.dp, height = 40.dp) // 버튼과 동일한 크기
                            .background(
                                color = Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                    )
                }
            }
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