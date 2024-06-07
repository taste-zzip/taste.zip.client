package com.example.tastezzip.model.response.cafeteria.detail

import com.example.tastezzip.model.enums.LikeType
import com.example.tastezzip.model.enums.TrophyType
import com.google.gson.annotations.SerializedName

data class AccountVideoMapping(
    @SerializedName("LIKE")
    val like: LikeType? = null,
    @SerializedName("TROPHY")
    val trophy: TrophyType = TrophyType.TROPHY,
    @SerializedName("STAR")
    val star: Int = 0
)
