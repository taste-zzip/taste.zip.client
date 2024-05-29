package com.example.tastezzip.model.request

data class SearchCafeteriaRequest(
    val page: Int,
    val size: Int,
    val sort: List<String>
)