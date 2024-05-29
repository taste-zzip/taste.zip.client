package com.example.tastezzip.model.response.shorts

data class YoutubeVideo(
    val channelId: String,
    val commentCount: Int,
    val description: String,
    val favoriteCount: Int,
    val likeCount: Int,
    val publishedAt: String,
    val thumbnail: String,
    val title: String,
    val viewCount: Int
)