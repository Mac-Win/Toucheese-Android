package com.toucheese.app.data.repository

import com.toucheese.app.data.model.qna.load_qnadetail.QnaDetailResponse
import com.toucheese.app.data.model.qna.load_qnalist.QnaListResponse
import com.toucheese.app.data.model.qna.update_qnadetail.UpdateQnaBody
import com.toucheese.app.data.model.qna.write_qnadetail.QnaDetailBody
import com.toucheese.app.data.network.QnaService
import javax.inject.Inject

class QnaRepository @Inject constructor(private val apiService: QnaService){
    // ----- 문의하기 API -----

    // 특정 문의하기 글 조회
    suspend fun loadQnaDetail(token: String?, questionId: Int): QnaDetailResponse = apiService.loadQnaDetail(token, questionId)

    // 문의하기 글 수정
    suspend fun updateQnaDetail(token: String?, questionId: Int, qnaBody: UpdateQnaBody): Unit = apiService.updateQnaDetail(token, questionId, qnaBody)

    // 문의하기 글 삭제
    suspend fun deleteQnaDetail(token: String?, questionId: Int): Unit = apiService.deleteQnaDetail(token, questionId)

    // 자신의 모든 문의하기 글 조회 (페이징 처리)
    suspend fun loadQnaList(token: String?, page: Int): QnaListResponse = apiService.loadQnaList(token, page)

    // 문의하기 글 생성
    suspend fun writeQnaDetail(token: String?, qnaDetail: QnaDetailBody) = apiService.writeQnaDetail(token, qnaDetail)
}