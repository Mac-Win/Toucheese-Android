package com.toucheese.app.data.model.qna.load_qnadetail


import com.google.gson.annotations.SerializedName

data class QnaDetailResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("createDate")
    val createDate: String,
    @SerializedName("answerResponse")
    val answerResponse: AnswerResponse,
    @SerializedName("authorName")
    val authorName: String,
    @SerializedName("answerStatus")
    val answerStatus: String,
    @SerializedName("imageUrls")
    val imageUrls: List<String>
)