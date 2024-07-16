package com.example.tastezzip.model.request.bookmark

import com.example.tastezzip.model.enums.LikeType

data class DeleteBookmarkRequestVo(
    val id: Long,
    val type: LikeType
)
