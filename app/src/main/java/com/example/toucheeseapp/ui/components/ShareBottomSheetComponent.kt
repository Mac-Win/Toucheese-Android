package com.example.toucheeseapp.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.toucheeseapp.R

@Composable
fun ShareBottomSheetComponent(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    context: Context,
    pageLink: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 제목
        Text(
            text = "공유하기",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
            modifier = Modifier.padding(16.dp)
        )

        // 공유 버튼 행
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ShareButton(
                iconRes = R.drawable.insta,
                description = "Instagram",
                label = "인스타그램",
                onClick = {
                    launchApp(context, "com.instagram.android")
                    onDismiss()
                }
            )
            ShareButton(
                iconRes = R.drawable.kakaotalk,
                description = "KakaoTalk",
                label = "카카오톡",
                onClick = {
                    launchApp(context, "com.kakao.talk")
                    onDismiss()
                }
            )
            ShareButton(
                iconRes = R.drawable.facebook,
                description = "Facebook",
                label = "페이스북",
                onClick = {
                    launchApp(context, "com.facebook.katana")
                    onDismiss()
                }
            )
            ShareButton(
                iconRes = R.drawable.link,
                description = "Copy Link",
                label = "링크 복사",
                onClick = {
                    copyToClipboard(context, pageLink)
                    onDismiss() }
            )
        }
    }
}

// 앱 실행
fun launchApp(context: Context, packageName: String) {
    try {
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        if (intent != null) {
            context.startActivity(intent)
        } else {
            val marketIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("market://details?id=$packageName")
            }
            context.startActivity(marketIntent)
        }
    } catch (e: Exception) {
        Toast.makeText(context, "앱을 실행할 수 없습니다.", Toast.LENGTH_SHORT).show()
    }
}

// 클립 보드 저장 (미완)
fun copyToClipboard(context: Context, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
    val clip = android.content.ClipData.newPlainText("Copied Link", text)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(context, "링크가 복사되었습니다.", Toast.LENGTH_SHORT).show()
}