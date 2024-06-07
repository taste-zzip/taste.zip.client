package com.example.tastezzip.model.response.shorts

import com.google.gson.annotations.SerializedName

data class YoutubeVideo(
    @SerializedName("channelId")
    val channelId: String,
    @SerializedName("commentCount")
    val commentCount: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("favoriteCount")
    val favoriteCount: Int,
    @SerializedName("likeCount")
    val likeCount: Int,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("viewCount")
    val viewCount: Int
)