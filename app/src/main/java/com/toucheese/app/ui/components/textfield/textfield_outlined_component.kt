package com.toucheese.app.ui.components.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun TextFieldOutlinedComponent(
    textFieldValue: String,
    placeholder: String,
    leadingIcon: ImageVector = Icons.Default.Add,
    showLeadingIcon: Boolean,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    onValueChanged: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None, // 압력 시 효과 적용
) {
    OutlinedTextField(
        value = textFieldValue,
        onValueChange = onValueChanged,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            focusedLabelColor = Color(0xFF262626),
        ),
        placeholder = {
            Text(
                text = placeholder,
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color(0xFFD9D9D9)
            )
        },
        singleLine = true,
        leadingIcon = if (showLeadingIcon) {
            {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = "id",
                    tint = Color(0xFFD9D9D9)
                )
            }
        } else null,
        keyboardOptions = keyboardOptions,
        modifier = modifier,
        visualTransformation = visualTransformation
    )

}