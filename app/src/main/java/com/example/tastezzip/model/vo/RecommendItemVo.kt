package com.example.tastezzip.model.vo

data class RecommendItemVo(
    val restaurantTitle: String = "",
    val restaurantType: String = "",
    val videoCount: Int = 0,
    val commentCount: Int = 0,
    val videoList: List<VideoItemVo> = emptyList()
)
