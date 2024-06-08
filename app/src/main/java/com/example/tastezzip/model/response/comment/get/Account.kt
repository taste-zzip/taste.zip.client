package com.example.tastezzip.model.response.comment.get

import com.google.gson.annotations.SerializedName

data class Account(
    @SerializedName("bio")
    val bio: String = "",
    @SerializedName("id")
    val id: Long = -1,
    @SerializedName("nickname")
    val nickname: String = "",
    @SerializedName("profileImage")
    val profileImage: String = ""
)