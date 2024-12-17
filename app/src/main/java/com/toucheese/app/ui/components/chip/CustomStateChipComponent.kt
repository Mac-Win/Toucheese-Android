package com.toucheese.app.ui.components.chip

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomStateChipComponent(
    label: String,
    disabledLabel: String,
    replyState: Boolean,
    borderColor: Color,
    disabledBorderColor: Color,
    containerColor: Color,
    disabledContainerColor: Color,
    labelColor: Color,
    disabledLabelColor: Color,
    modifier: Modifier = Modifier,
    onClickListener: () -> Unit = { }
    ) {
    SuggestionChip(
        shape = RoundedCornerShape(6.dp),
        border = if(replyState) BorderStroke(1.dp, borderColor) else BorderStroke(1.dp, disabledBorderColor),
        enabled = replyState,
        label = {
            Text(
                text = if (replyState) label else disabledLabel,
                style = MaterialTheme.typography.labelSmall,
            )
        },
        modifier = modifier,
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = containerColor,
            disabledContainerColor = disabledContainerColor,
            labelColor = labelColor,
            disabledLabelColor = disabledLabelColor
        ),
        onClick = onClickListener,
    )
}