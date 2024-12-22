package com.toucheese.app.data.model.qna.update_qnadetail


import com.google.gson.annotations.SerializedName

data class UpdateQnaBody(
    @SerializedName("content")
    val content: String,
    @SerializedName("title")
    val title: String
)