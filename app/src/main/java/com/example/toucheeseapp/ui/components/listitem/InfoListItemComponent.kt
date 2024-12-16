package com.example.toucheeseapp.ui.components.listitem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.toucheeseapp.ui.theme.ToucheeseAppTheme

@Composable
fun InfoListItemComponent(
    title: String,
    content: String = "",
    createDate: String,
    userName: String,
    replyState: Boolean,
    isContentShowed: Boolean,
    modifier: Modifier = Modifier,
    onItemClicked: () -> Unit,
) {
    OutlinedCard(
        enabled = replyState,
        shape = CardDefaults.outlinedShape,
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = Color(0xFFECECEC)
        ),
        onClick = onItemClicked,
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                SuggestionChip(
                    shape = RoundedCornerShape(50.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer),
                    label = {
                        Text(
                            text = userName,
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    modifier = Modifier.wrapContentHeight(),
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    onClick = { /* 필요 시 연결 및 구현 */},
                )


                SuggestionChip(
                    shape = RoundedCornerShape(999.dp),
                    border = if(replyState) BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer) else BorderStroke(1.dp, Color(0xFFECECECE)),
                    enabled = replyState,
                    label = {
                        Text(
                            text = if (replyState) "답변 완료" else "답변 대기",
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    modifier = Modifier
                        .wrapContentHeight()
                        .clickable(enabled = false) {},
                    colors = SuggestionChipDefaults.suggestionChipColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        disabledContainerColor = Color(0xFFECECEC)
                    ),
                    onClick = { /* 필요 시 연결 및 구현 */},
                )
            }

            if (isContentShowed) {
                if (content.isNotEmpty()) {
                    Text(
                        text = content,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF434343)
                    )
                } else {
                    Text(
                        text = "작성해주신 문의 내용이 없습니다.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }

}

@Preview
@Composable
private fun InfoListItemPreview() {
    val title = "제목을 입력해주세요"
    val content = "문의 내용입니다.\n문의 내용입니다.문의 내용입니다.문의 내용입니다.문의 내용입니다.문의 내용입니다.문의 내용입니다. 문의 내용입니다."
    val createDate = "24.11.04"
    val userName = "홍길동"
    val replyState = true
    val isContentShowed = true
    ToucheeseAppTheme {
        InfoListItemComponent(
            title = title,
            content = content,
            createDate = createDate,
            userName = userName,
            replyState = replyState,
            isContentShowed = isContentShowed,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 16.dp),
            onItemClicked = {},
        )
    }
}
