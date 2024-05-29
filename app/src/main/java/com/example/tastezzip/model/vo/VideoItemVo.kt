package com.example.tastezzip.model.vo

data class VideoItemVo(
    val id: Long = -1,
    val platform: String = "",
    val videoPk: String = "",
    val status: String = "",
    val starCount: Double = 0.0,
    val trophyCount: Int = 0,
    val thumbnailUrl: String = "",
    val title: String = "",
    val viewCount: Int = 0
)