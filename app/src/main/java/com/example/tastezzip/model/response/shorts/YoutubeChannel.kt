package com.example.tastezzip.model.response.shorts

data class YoutubeChannel(
    val channelUrl: String,
    val customId: String,
    val description: String,
    val publishedAt: String,
    val subscriberCount: Int,
    val title: String,
    val videoCount: Int,
    val viewCount: Int
)