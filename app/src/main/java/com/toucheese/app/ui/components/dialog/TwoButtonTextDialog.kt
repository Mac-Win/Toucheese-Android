package com.toucheese.app.ui.components.dialog

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.time.LocalDate
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TwoButtonTextDialog(
    date: LocalDate,
    time: LocalTime,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onConfirmClicked: () -> Unit,
    onCancelClicked: () -> Unit,
) {
    Dialog(
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        ),
        onDismissRequest = onDismissRequest,
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp),
            ) {
                Text(
                    text = "${date.year}년 ${date.monthValue}월 ${date.dayOfMonth}일 ${time.hour}:${time.minute}로\n" +
                            "예약일정을 변경하시겠습니까?",
                )

                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // 아니오
                    Button(
                        onClick = onCancelClicked
                    ) {
                        Text(
                            text = "아니오"
                        )
                    }

                    // 예
                    Button(
                        onClick = onConfirmClicked
                    ) {
                        Text(
                            text = "아니오"
                        )
                    }

                }

            }

        }
    }


}