package com.toucheese.app.ui.components.textbutton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun IconButtonComponent(
    icon: ImageVector,
    label: String,
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onButtonClicked: () -> Unit,
) {
    Button(
        shape = RoundedCornerShape(8.dp),
        enabled = enabled,
        modifier = Modifier.wrapContentSize(),
        onClick = onButtonClicked
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // + 아이콘
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.padding(end = 4.dp)
            )
            // 문구
            Text(
                text = label
            )

        }
    }
}
