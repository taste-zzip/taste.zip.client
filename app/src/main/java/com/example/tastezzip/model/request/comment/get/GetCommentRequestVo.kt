package com.example.tastezzip.model.request.comment.get

data class GetCommentRequestVo(
    val page: Int,
    val size: Int,
    val sort: List<String>
)