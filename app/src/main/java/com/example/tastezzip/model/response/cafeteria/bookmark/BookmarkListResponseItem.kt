package com.example.tastezzip.model.response.cafeteria.bookmark

import com.example.tastezzip.model.response.worldcup.WorldCupListResponseItem
import com.google.gson.annotations.SerializedName

data class BookmarkListResponseItem(
    @SerializedName("cafeteria")
    val cafeteria: Cafeteria,
    @SerializedName("videoList")
    val videoList: List<WorldCupListResponseItem>
)
