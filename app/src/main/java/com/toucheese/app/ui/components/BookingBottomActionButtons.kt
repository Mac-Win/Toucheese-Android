package com.toucheese.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toucheese.app.ui.theme.ToucheeseAppTheme

@Composable
fun BookingBottomActionButtons(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit = {},
    onChangeClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp), // 전체 패딩 설정
        horizontalArrangement = Arrangement.spacedBy(16.dp) // 버튼 간 간격 설정
    ) {
        // 예약 취소 버튼
        Button(
            onClick = onCancelClick,
            modifier = Modifier
                .weight(1f) // 동일한 비율로 너비 설정
                .height(48.dp), // 버튼 높이 설정
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "예약 취소",
                color = MaterialTheme.colorScheme.onError,
                style = MaterialTheme.typography.titleMedium)
        }

        // 예약 변경 버튼
        Button(
            onClick = onChangeClick,
            modifier = Modifier
                .weight(1f) // 동일한 비율로 너비 설정
                .height(48.dp), // 버튼 높이 설정
            colors = ButtonDefaults.buttonColors(), // 기본 버튼 색상 사용
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(text = "예약 변경",
                style = MaterialTheme.typography.titleMedium)
        }
    }
}



@Preview
@Composable
fun BookingBottomActionButtonsPreview(){
    ToucheeseAppTheme {
        Surface {
            BookingBottomActionButtons(
                modifier = Modifier
            )
        }
    }
}