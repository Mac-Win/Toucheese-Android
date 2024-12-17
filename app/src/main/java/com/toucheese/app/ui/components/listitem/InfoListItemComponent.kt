package com.toucheese.app.ui.components.listitem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.toucheese.app.ui.theme.ToucheeseAppTheme

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
        shape = CardDefaults.outlinedShape,
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        onClick = onItemClicked,
    ) {
        Column(
            modifier = modifier,
        ) {

            // 제목
            Row {
                Text(
                    text = "Q.",
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            // 문의 이미지
            Row {

            }

            // 작성자 및 작성일
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = userName,
                    style = MaterialTheme.typography.labelSmall
                )


                Text(
                    text = "| 작성일: $createDate",
                    style = MaterialTheme.typography.labelSmall
                )
            }
                SuggestionChip(
                    shape = RoundedCornerShape(6.dp),
                    border = if(replyState) BorderStroke(1.dp, Color(0xFFFFC999)) else BorderStroke(1.dp, Color(0xFFD9D9D9)),
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
                        containerColor = Color(0xFFFFFDE6),
                        disabledContainerColor = MaterialTheme.colorScheme.background,
                        labelColor = Color(0xFFFFC999),
                        disabledLabelColor = Color(0xFF8C8C8C)
                    ),
                    onClick = { /* 필요 시 연결 및 구현 */},
                )

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
