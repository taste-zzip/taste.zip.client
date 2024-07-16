package com.example.tastezzip.model.response.cafeteria.recommendation

import com.google.gson.annotations.SerializedName

data class RecommendResponseItem(
    @SerializedName("address")
    val address: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("videoCnt")
    val videoCnt: Int,
    @SerializedName("videos")
    val videos: List<Video>
)