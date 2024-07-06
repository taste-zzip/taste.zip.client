package com.example.tastezzip.ui.base

import com.google.gson.annotations.SerializedName

data class ApiResponse<D>(
    @SerializedName("data")
    val data : D? = null,
    @SerializedName("status")
    val code : Int = 0
)
