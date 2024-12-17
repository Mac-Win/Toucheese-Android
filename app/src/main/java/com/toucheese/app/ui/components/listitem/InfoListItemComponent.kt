package com.toucheese.app.ui.components.listitem

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.toucheese.app.ui.components.chip.CustomStateChipComponent
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
    val list = listOf(
        "https://i.imgur.com/1JyMHIq.jpeg",
        "https://i.imgur.com/kW8AmQ3.jpeg",
        "https://i.imgur.com/ddUrPZH.jpeg",
        "https://i.imgur.com/noTkeSZ.jpeg",
        "https://i.imgur.com/EEVgYuZ.jpeg",
    )
    OutlinedCard(
        shape = CardDefaults.outlinedShape,
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        onClick = onItemClicked,
    ) {
        Box(
            modifier = modifier
        ) {
            Column {

                // 제목
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Q.",
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier= Modifier.weight(1f))

                    CustomStateChipComponent(
                        label = "답변 완료",
                        disabledLabel = "답변 대기",
                        replyState = replyState,
                        containerColor = Color(0xFFFFFDE6),
                        disabledContainerColor = MaterialTheme.colorScheme.background,
                        borderColor = Color(0xFFFFC999),
                        disabledBorderColor = Color(0xFFD9D9D9),
                        labelColor = Color(0xFFFFC999),
                        disabledLabelColor = Color(0xFF8C8C8C),
                        modifier = Modifier
                            .height(30.dp)
//                            .align(Alignment.TopEnd)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

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

                Spacer(modifier = Modifier.height(12.dp))
                // 문의 이미지
                if (list.isNotEmpty()){
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(list) { item ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth(),
                            ) {
                                AsyncImage(
                                    model = item,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    alignment = Alignment.Center,
                                    modifier = Modifier.size(80.dp)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                // 작성자 및 작성일
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = userName,
                        style = MaterialTheme.typography.labelSmall
                    )


                    Text(
                        text = " | 작성일: $createDate",
                        style = MaterialTheme.typography.labelSmall
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
