package com.example.tastezzip.model.request

import com.example.tastezzip.model.enums.LikeType

data class AddLikeVideoRequestVo(
    val videoId: Long,
    val type: LikeType,
    val score: Double?
)
