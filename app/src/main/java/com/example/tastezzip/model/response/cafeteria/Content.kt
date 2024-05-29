package com.example.tastezzip.model.response.cafeteria

import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("city")
    val city: String,
    @SerializedName("district")
    val district: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("landAddress")
    val landAddress: String,
    @SerializedName("latitude")
    val latitude: String,
    @SerializedName("longitude")
    val longitude: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("neighborhood")
    val neighborhood: String,
    @SerializedName("streetAddress")
    val streetAddress: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("videoCnt")
    val videoCnt: Int
)