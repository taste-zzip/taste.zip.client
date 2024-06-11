package com.example.tastezzip.model.response.worldcup

import com.example.tastezzip.model.response.cafeteria.detail.CafeteriaDetailResponse
import com.google.gson.annotations.SerializedName

data class WorldCupListResponseItem(
    @SerializedName("accountVideoMapping")
    val accountVideoMapping: AccountVideoMapping? = null,
    @SerializedName("id")
    val id: Long = -1,
    @SerializedName("platform")
    val platform: String = "",
    @SerializedName("starAverage")
    val starAverage: Double = 0.0,
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
    @SerializedName("cafeteriaResponse")
    val cafeteriaInfo: CafeteriaDetailResponse = CafeteriaDetailResponse()
)