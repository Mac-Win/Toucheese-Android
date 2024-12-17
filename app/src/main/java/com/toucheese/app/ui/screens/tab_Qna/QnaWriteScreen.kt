package com.toucheese.app.ui.screens.tab_Qna

import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.toucheese.app.ui.components.BottomNavigationBarComponent
import com.toucheese.app.ui.components.topbar.TopAppBarComponent
import com.toucheese.app.ui.viewmodel.QnaViewModel

@Composable
fun QnaWriteScreen(
    selectedTab: Int,
    viewModel: QnaViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onClickLeadingIcon: () -> Unit,
    onTabSelected: (Int) -> Unit,
    onEnrolledClicked: () -> Unit,
) {
    // 문의 제목
    val (textFieldTitle, setTitle) = remember { mutableStateOf("") }
    // 문의 내용
    val (textFieldContent, setContent) = remember { mutableStateOf("") }
    // 문의 작성했는지 여부
    val isValidate: Boolean = textFieldTitle.isNotBlank() && textFieldContent.isNotBlank()
    // 사진 촬영 및 앨범 선택 드롭다운 확장 여부
    val (dropdownSate, setDropdownState) = remember { mutableStateOf(false) }
    // 사진 삭제 여부 드롭다운 확장 여부
    val (deleteDropdownState, setDeleteDropdownState) = remember { mutableStateOf(false) }
    // context
    val context = LocalContext.current
    // 상태 저장
    val mediaList = remember { mutableStateListOf<Media>() }

    // 카메라 런처
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        bitmap?.let { mediaList.add(Media.ImageBitmap(it)) }
    }

    // 갤러리 런처
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { mediaList.add(Media.ImageUri(it)) }
    }

    Scaffold(
        topBar = {
            TopAppBarComponent(
                title = "문의하기",
                leadingIcon = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                showLeadingIcon = true,
                showTrailingIcon = false,
                onClickLeadingIcon = onClickLeadingIcon,
            )
        },
        bottomBar = {
            BottomNavigationBarComponent(
                selectedTab = selectedTab,
                onTabSelected = onTabSelected,
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(40.dp))
            }

            // 제목
            item {
                Text(
                    text = "제목",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(
                    value = textFieldTitle,
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = setTitle,
                    keyboardOptions = KeyboardOptions().copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFFECECEC)
                    ),
                    placeholder = {
                        Text(
                            text = "제목을 입력해주세요.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
            }

            // 내용
            item {
                Text(
                    text = "내용",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(
                    value = textFieldContent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 300.dp), // 최소 높이 설정
                    onValueChange = setContent,
                    keyboardOptions = KeyboardOptions().copy(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color(0xFFECECEC)
                    ),
                    visualTransformation = VisualTransformation.None,
                    shape = RoundedCornerShape(12.dp),
                    placeholder = {
                        Text(
                            text = "문의내용을 입력해주세요.",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                )
            }

            // 이미지 및 영상
            item {
                Box(
                    modifier = Modifier
                        .padding(top = 28.dp, bottom = 28.dp, end = 16.dp)
                ) {
                    DropdownMenu(
                        expanded = dropdownSate,
                        onDismissRequest = {
                            setDropdownState(false)
                        },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .wrapContentSize()
                            .background(color = MaterialTheme.colorScheme.surface),

                        ) {
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.ThumbUp,
                                    contentDescription = null
                                )
                            },
                            text = {
                                Text(
                                    text = "사진 찍기"
                                )
                            },
                            onClick = {
                                // 카메라로 사진 촬영
                                cameraLauncher.launch()
                                // 드롭다운 닫기
                                setDropdownState(false)
                            }
                        )

                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Face,
                                    contentDescription = null
                                )
                            },
                            text = {
                                Text(
                                    text = "앨범에서 선택"
                                )
                            },
                            onClick = {
                                // 앨범에서 사진 선택
                                galleryLauncher.launch("image/*")
                                // 드롭다운 닫기
                                setDropdownState(false)
                            },
                            modifier = Modifier.wrapContentSize()
                        )
                    }
                    LazyRow(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        item {
                            OutlinedCard(
                                shape = RoundedCornerShape(6.dp),
                                border = BorderStroke(2.dp, Color(0xFFECECEC)),
                                modifier = Modifier
                                    .pointerInput(Unit) {
                                        // 사용자 제스처 감지
                                        detectTapGestures(
                                            onTap = {
                                                // 드롭다운을 띄워준다(사진직기, 앨범, 등)
                                                setDropdownState(true)
                                            }
                                        )
                                    }
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.size(84.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ThumbUp,
                                        contentDescription = null
                                    )
                                    Text(
                                        text = "${mediaList.size}/5"
                                    )
                                }
                            }
                        }

                        items(mediaList) { media ->
                            DropdownMenu(
                                expanded = deleteDropdownState,
                                onDismissRequest = {
                                    setDeleteDropdownState(false)
                                },
                                modifier = Modifier
                                    .wrapContentSize()
                                    .background(
                                        color = MaterialTheme.colorScheme.surface
                                    )
                            ) {
                                DropdownMenuItem(
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Default.ThumbUp,
                                            contentDescription = null
                                        )
                                    },
                                    text = {
                                        Text(
                                            text = "삭제하기"
                                        )
                                    },
                                    onClick = {
                                        mediaList.remove(media)
                                        // 드롭다운 닫기
                                        setDeleteDropdownState(false)
                                    },
                                )
                            }
                            OutlinedCard(
                                shape = RoundedCornerShape(6.dp),
                                border = BorderStroke(2.dp, Color(0xFFECECEC)),
                                modifier = Modifier
                                    .pointerInput(Unit) {
                                        // 사용자 제스처 감지
                                        detectTapGestures(
                                            onLongPress = {
                                                // 이미지 삭제 드롭다운 띄우기
                                                setDeleteDropdownState(true)
                                            }
                                        )
                                    },
                            ) {
                                when (media) {
                                    // 선택된 이미지 표시
                                    is Media.ImageUri -> {
                                        Image(
                                            painter = rememberAsyncImagePainter(media.uri),
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .size(84.dp)
                                        )
                                    }

                                    // 촬영된 이미지 표시
                                    is Media.ImageBitmap -> {
                                        Image(
                                            bitmap = media.bitmap.asImageBitmap(),
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .size(84.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 문의 등록
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 36.dp)
                ) {
                    Button(
                        enabled = isValidate,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            disabledContainerColor = Color(0xFFECECEC)
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 14.dp, horizontal = 8.dp),
                        onClick = onEnrolledClicked,
                    ) {
                        Text(
                            text = "문의 등록",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                }
            }
        }
    }
}

// 미디어 데이터 타입 정의
sealed class Media {
    data class ImageUri(val uri: Uri) : Media()
    data class ImageBitmap(val bitmap: Bitmap) : Media()
}