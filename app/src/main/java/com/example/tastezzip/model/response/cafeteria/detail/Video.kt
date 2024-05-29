package com.example.tastezzip.model.response.cafeteria.detail

import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("id")
    val id: Long = -1,
    @SerializedName("platform")
    val platform: String = "",
    @SerializedName("starAverage")
    val starAverage: Int = 0,
    @SerializedName("status")
    val status: String = "",
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String = "",
    @SerializedName("title")
    val title: String = "",
    @SerializedName("trophyCount")
    val trophyCount: Int = 0,
    @SerializedName("videoPk")
    val videoPk: String = "",
    @SerializedName("videoUrl")
    val videoUrl: String = "",
    @SerializedName("viewCount")
    val viewCount: Int = 0,
    @SerializedName("accountVideoMapping")
    val accountVideoMapping: AccountVideoMapping
)