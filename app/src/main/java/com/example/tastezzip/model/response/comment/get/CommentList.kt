package com.example.tastezzip.model.response.comment.get

import com.google.gson.annotations.SerializedName

data class CommentList(
    @SerializedName("content")
    val content: List<Content> = emptyList()
)