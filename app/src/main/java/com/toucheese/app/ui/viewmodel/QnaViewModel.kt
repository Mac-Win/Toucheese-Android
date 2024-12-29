package com.toucheese.app.ui.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toucheese.app.data.model.qna.load_qnadetail.QnaDetailResponse
import com.toucheese.app.data.model.qna.load_qnalist.QnaListItem
import com.toucheese.app.data.model.qna.update_qnadetail.UpdateQnaBody
import com.toucheese.app.data.repository.QnaRepository
import com.toucheese.app.ui.screens.tab_Qna.Media
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class QnaViewModel @Inject constructor(private val repository: QnaRepository): ViewModel() {
    private val TAG = "QnaViewModel"
    // 자신의 모든 문의 글 리스트
    private val _qnaList = MutableStateFlow<List<QnaListItem>>(emptyList())
    val qnaList: StateFlow<List<QnaListItem>> = _qnaList

    // 특정 문의하기 글 조회
    suspend fun loadQnaDetail(token: String?, questionId: Int): QnaDetailResponse?{
        return try {
            repository.loadQnaDetail(
                token = "Bearer $token",
                questionId = questionId
            )
        } catch (error: Exception){
            Log.d(TAG, "문의 글 조회 error: ${error.message}")
            null
        }
    }

    // 문의하기 글 수정
    fun updateQnaDetail(token: String?, questionId: Int, title: String, content: String){
        val qnaBody = UpdateQnaBody(title, content)
        viewModelScope.launch {
            try {
                repository.updateQnaDetail(
                    token = "Bearer $token",
                    questionId = questionId,
                    qnaBody = qnaBody
                )
            } catch (error: Exception){
                Log.d(TAG, "문의 글 수정 error: ${error.message}")
            }
        }
    }

    // 문의하기 글 삭제
    fun deleteQnaDetail(token: String?, questionId: Int){
        viewModelScope.launch {
            try {
                repository.deleteQnaDetail(
                    token = "Bearer $token",
                    questionId = questionId
                )
            } catch (error: Exception){
                Log.d(TAG, "문의 글 삭제 error: ${error.message}")
            }
        }
    }

    // 자신의 모든 문의하기 글 조회 (페이징 처리)
    fun loadQnaList(token: String?, page: Int = 0){
        viewModelScope.launch {
            try {
                val result = repository.loadQnaList(
                    token = "Bearer $token",
                    page = page
                )
                _qnaList.value = result.qnaListItem
            } catch (error: Exception){
                Log.d(TAG, "모든 문의 글 조회 error: ${error.message}")
            }
        }
    }

    // 문의하기 글 생성
    fun writeQnaDetail(
        token: String?,
        title: String,
        content: String,
        mediaList: List<Media>,
        context: Context
        ){
        viewModelScope.launch {
            try {
                // RequestBody로 변환
                val titleBody: RequestBody = title.toRequestBody("text/plain".toMediaTypeOrNull())
                val contentBody: RequestBody = content.toRequestBody("text/plain".toMediaTypeOrNull())
                val uploadFiles: List<MultipartBody.Part> = mediaList.mapIndexedNotNull { index, media ->
                    mediaToFile(context, media)?.let { file ->
                        MultipartBody.Part.createFormData(
                            name = "uploadFiles", // 배열 형식의 이름 사용
                            filename = file.name,
                            body = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                        )
                    }
                }

                // withContext: 특정 작업을 취소되지 않는 컨텍스트
                withContext(NonCancellable){
                    repository.writeQnaDetail(
                        token = "Bearer $token",
                        title = titleBody,
                        content = contentBody,
                        uploadFiles = uploadFiles
                    )
                }
                Log.d(TAG, "문의 글 생성완료?")
            } catch (error: Exception){
                Log.e(TAG, "문의 글 생성 error : ${error.message}")
            }
        }
    }

    companion object {

        fun mediaToFile(context: Context, media: Media): File? {
            return when (media) {
                is Media.ImageUri -> uriToFile(context, media.uri)
                is Media.ImageBitmap -> bitmapToFile(context, media.bitmap)
            }
        }

        fun uriToFile(context: Context, uri: Uri): File? {
            val contentResolver = context.contentResolver
            val inputStream = contentResolver.openInputStream(uri) ?: return null
            val tempFile = File(context.cacheDir, "temp_image.jpg")
            FileOutputStream(tempFile).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
            return tempFile
        }

        fun bitmapToFile(context: Context, bitmap: Bitmap): File? {
            val tempFile = File(context.cacheDir, "temp_image.jpg")
            FileOutputStream(tempFile).use { outputStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            }
            return tempFile
        }
    }
}