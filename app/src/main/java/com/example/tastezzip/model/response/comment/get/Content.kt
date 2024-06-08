package com.example.tastezzip.model.response.comment.get

import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("account")
    val account: Account,
    @SerializedName("comment")
    val comment: Comment
)