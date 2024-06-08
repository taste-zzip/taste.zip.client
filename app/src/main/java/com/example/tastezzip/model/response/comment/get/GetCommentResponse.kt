package com.example.tastezzip.model.response.comment.get

import com.google.gson.annotations.SerializedName

data class GetCommentResponse(
    @SerializedName("commentList")
    val commentList: CommentList
)