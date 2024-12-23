package com.toucheese.app.data.model.qna.write_qnadetail


import com.google.gson.annotations.SerializedName

data class QnaDetailBody(
    @SerializedName("content")
    val content: String,
    @SerializedName("title")
    val title: String
)