package com.toucheese.app.data.model.qna.load_qnadetail


import com.google.gson.annotations.SerializedName

data class QnaDetailResponse(
    @SerializedName("answerResponse")
    val answerResponse: AnswerResponse,
    @SerializedName("answerStatus")
    val answerStatus: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("createDate")
    val createDate: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String
)