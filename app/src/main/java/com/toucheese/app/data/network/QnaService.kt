package com.toucheese.app.data.network

import com.toucheese.app.data.model.qna.load_qnadetail.QnaDetailResponse
import com.toucheese.app.data.model.qna.load_qnalist.QnaListResponse
import com.toucheese.app.data.model.qna.update_qnadetail.UpdateQnaBody
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface QnaService {

    // ----- 문의하기 API -----

    // 특정 문의하기 글 조회
    @GET("v1/questions/{questionId}")
    suspend fun loadQnaDetail(
        @Header("Authorization") token: String?,
        @Path("questionId") questionId: Int,
    ): QnaDetailResponse

    // 문의하기 글 수정
    @PUT("v1/questions/{questionId}")
    suspend fun updateQnaDetail(
        @Header("Authorization") token: String?,
        @Path("questionId") questionId: Int,
        @Body qnaBody: UpdateQnaBody
    )

    // 문의하기 글 삭제
    @DELETE("v1/questions/{questionId}")
    suspend fun deleteQnaDetail(
        @Header("Authorization") token: String?,
        @Path("questionId") questionId: Int,
    )

    // 자신의 모든 문의하기 글 조회 (페이징 처리)
    @GET("v1/questions")
    suspend fun loadQnaList(
        @Header("Authorization") token: String?,
        @Query("page") page: Int,
    ): QnaListResponse

    // 문의하기 글 생성
    @Multipart
    @POST("v1/questions")
    suspend fun writeQnaDetail(
        @Header("Authorization") token: String?,
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part uploadFiles: List<MultipartBody.Part>
    )
}