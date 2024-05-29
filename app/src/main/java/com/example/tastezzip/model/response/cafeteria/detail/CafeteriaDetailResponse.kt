package com.example.tastezzip.model.response.cafeteria.detail

import com.google.gson.annotations.SerializedName

data class CafeteriaDetailResponse(
    @SerializedName("address")
    val address: String = "",
    @SerializedName("id")
    val id: Long = -1,
    @SerializedName("latitude")
    val latitude: String = "",
    @SerializedName("longitude")
    val longitude: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("type")
    val type: String = "",
    @SerializedName("videoCnt")
    val videoCnt: Int = -1,
    @SerializedName("videos")
    val videos: List<Video> = emptyList()
)