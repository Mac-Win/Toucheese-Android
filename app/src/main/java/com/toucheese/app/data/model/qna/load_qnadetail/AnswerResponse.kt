package com.toucheese.app.data.model.qna.load_qnadetail


import com.google.gson.annotations.SerializedName

data class AnswerResponse(
    @SerializedName("content")
    val content: String,
    @SerializedName("createDate")
    val createDate: String,
    @SerializedName("id")
    val id: Int
)