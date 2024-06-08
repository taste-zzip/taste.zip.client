package com.example.tastezzip.model.response.comment.get

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("content")
    val content: String = "",
    @SerializedName("createdAt")
    val createdAt: String = "",
    @SerializedName("id")
    val id: Long = -1,
    @SerializedName("updatedAt")
    val updatedAt: String = ""
)