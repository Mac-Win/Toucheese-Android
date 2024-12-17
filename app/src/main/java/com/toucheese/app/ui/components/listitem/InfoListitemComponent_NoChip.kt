package com.toucheese.app.ui.components.listitem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toucheese.app.ui.theme.ToucheeseAppTheme

@Composable
fun InfoListItemComponentNoChip(
    title: String,
    content: String = "",
    createDate: String,
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        shape = CardDefaults.outlinedShape,
        colors = CardDefaults.outlinedCardColors(
            containerColor = Color(0xFFFFFDE6),
            disabledContainerColor = Color(0xFFECECEC)
        ),
    ) {
        Column(
            modifier = modifier
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "작성일: $createDate",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF434343),
            )
        }
    }
}


@Preview
@Composable
private fun InfoListItemNoChipPreview() {
    ToucheeseAppTheme {
        InfoListItemComponentNoChip(
            title = "터치즈 담당자",
            content = "안녕하세요 OOO님.\n 문의내용 답변이 들어갑니다. 감사합니다.",
            createDate = "2024-11-04",
            modifier = Modifier.fillMaxWidth().padding(
                start = 12.dp,
                end = 12.dp,
                bottom = 12.dp,
                top = 16.dp
            ),
        )
    }
}