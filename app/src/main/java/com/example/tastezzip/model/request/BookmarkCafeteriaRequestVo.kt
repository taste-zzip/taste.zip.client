package com.example.tastezzip.model.request

data class BookmarkCafeteriaRequestVo(
    val cafeteriaId: Long,
    val type: String = "LIKE"
)
