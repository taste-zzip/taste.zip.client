package com.example.tastezzip.model.request

import com.example.tastezzip.model.enums.LikeType

data class DeleteLikeVideoRequestVo (
    val videoId: Long,
    val type: LikeType = LikeType.LIKE
)