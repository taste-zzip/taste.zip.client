package com.example.tastezzip.model.response.shorts

import com.example.tastezzip.model.response.cafeteria.detail.AccountVideoMapping

data class Feed(
    val accountVideoMapping: AccountVideoMapping,
    val cafeteria: Cafeteria,
    val statistic: Statistic,
    val video: Video,
    val youtubeChannel: YoutubeChannel,
    val youtubeVideo: YoutubeVideo
)