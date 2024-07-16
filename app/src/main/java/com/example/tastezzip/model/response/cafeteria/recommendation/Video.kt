package com.example.tastezzip.model.response.cafeteria.recommendation

import com.example.tastezzip.model.response.cafeteria.detail.AccountVideoMapping
import com.example.tastezzip.model.response.cafeteria.detail.CafeteriaDetailResponse
import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("accountVideoMapping")
    val accountVideoMapping: AccountVideoMapping = AccountVideoMapping(),
    @SerializedName("cafeteriaResponse")
    val cafeteriaResponse: CafeteriaDetailResponse,
    @SerializedName("id")
    val id: Long,
    @SerializedName("starAverage")
    val starAverage: Float,
    @SerializedName("status")
    val status: String,
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("trophyCount")
    val trophyCount: Int,
    @SerializedName("videoPk")
    val videoPk: String,
    @SerializedName("videoUrl")
    val videoUrl: String,
    @SerializedName("viewCount")
    val viewCount: Int
)