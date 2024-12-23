package com.toucheese.app.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.toucheese.app.data.model.qna.load_qnadetail.QnaDetailResponse
import com.toucheese.app.data.model.qna.load_qnalist.QnaListItem
import com.toucheese.app.data.model.qna.update_qnadetail.UpdateQnaBody
import com.toucheese.app.data.model.qna.write_qnadetail.QnaDetailBody
import com.toucheese.app.data.repository.QnaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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
                _qnaList.value = result.qnaList
            } catch (error: Exception){
                Log.d(TAG, "모든 문의 글 조회 error: ${error.message}")
            }
        }
    }

    // 문의하기 글 생성
    fun writeQnaDetail(token: String?, title: String, content: String){
        val qnaDetail = QnaDetailBody(title = title, content =  content)
        viewModelScope.launch {
            try {
                repository.writeQnaDetail(
                    token = "Bearer $token",
                    qnaDetail = qnaDetail
                )
            } catch (error: Exception){
                Log.d(TAG, "문의 글 생성 ${error.message}")
            }
        }
    }
}